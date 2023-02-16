package com.programacho.paymentgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programacho.paymentgateway.log.LogIngesterPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class LogResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(LogResponseBodyAdvice.class);

    private final StreamBridge streamBridge;

    private final ObjectMapper mapper;

    public LogResponseBodyAdvice(StreamBridge streamBridge, ObjectMapper mapper) {
        this.streamBridge = streamBridge;
        this.mapper = mapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("RabbitMQにpayment-gatewayのレスポンス内容を送信します。");
        try {
            streamBridge.send("log-in-0", new LogIngesterPayload("payment-gateway", "trace", "response", mapper.writeValueAsString(body)));
        } catch (JsonProcessingException e) {
            log.debug("メッセージオブジェクトをJSON形式にパースすることに失敗しました。");
        } catch (RuntimeException e) {
            log.debug("RabbitMQへのメッセージ送信に失敗しました。");
        }

        return body;
    }
}
