package com.programacho.paymentgateway;

import com.programacho.paymentgateway.log.LogIngesterPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class LogRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(LogRestTemplateInterceptor.class);

    private final StreamBridge streamBridge;


    public LogRestTemplateInterceptor(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.debug("RabbitMQに対向決済機関へのリクエスト内容を送信します。");

        try {
            streamBridge.send("log-in-0", new LogIngesterPayload(
                    "payment-gateway",
                    "payment",
                    "request",
                    new String(body, StandardCharsets.UTF_8)
            ));
        } catch (RuntimeException e) {
            log.debug("RabbitMQへのメッセージ送信に失敗しました。");
        }

        ClientHttpResponse response = execution.execute(request, body);

        log.debug("RabbitMQに対向決済機関からのレスポンス内容を送信します。");

        BufferingClientHttpResponseWrapper responseWrapper = new BufferingClientHttpResponseWrapper(response);
        try {
            streamBridge.send("log-in-0", new LogIngesterPayload(
                    "payment-gateway",
                    "payment",
                    "response",
                    new String(responseWrapper.getBody().readAllBytes(), StandardCharsets.UTF_8)
            ));
        } catch (RuntimeException e) {
            log.debug("RabbitMQへのメッセージ送信に失敗しました。");
        }

        return responseWrapper;
    }

    private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;

        @Nullable
        private byte[] body;


        BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
            this.response = response;
        }


        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return this.response.getStatusCode();
        }

        @Override
        @Deprecated
        public int getRawStatusCode() throws IOException {
            return this.response.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.response.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            if (this.body == null) {
                this.body = StreamUtils.copyToByteArray(this.response.getBody());
            }
            return new ByteArrayInputStream(this.body);
        }

        @Override
        public void close() {
            this.response.close();
        }

    }
}
