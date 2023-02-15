package com.programacho.paymentgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(LogRestTemplateInterceptor.class);

    private final StreamBridge streamBridge;


    public LogRestTemplateInterceptor(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        streamBridge.send("log-in-0", "preExchange");

        ClientHttpResponse response = execution.execute(request, body);

        streamBridge.send("log-in-0", "postExchange");

        return response;
    }
}
