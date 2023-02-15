package com.programacho.paymentgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@RestControllerAdvice
public class LogRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final Logger log = LoggerFactory.getLogger(LogRequestBodyAdviceAdapter.class);

    private final StreamBridge streamBridge;

    private final ObjectMapper mapper;

    public LogRequestBodyAdviceAdapter(StreamBridge streamBridge, ObjectMapper mapper) {
        this.streamBridge = streamBridge;
        this.mapper = mapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("RabbitMQにpayment-gatewayへのリクエスト内容を送信します。");
        try {
            streamBridge.send("log-in-0", new LogIngesterPayload("payment-gateway", "trace", "request", mapper.writeValueAsString(body)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return body;
    }
}
