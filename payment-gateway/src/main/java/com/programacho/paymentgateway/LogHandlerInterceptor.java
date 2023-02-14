package com.programacho.paymentgateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LogHandlerInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    private final StreamBridge streamBridge;

    public LogHandlerInterceptor(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        log.info("preHandle");
        streamBridge.send("log-in-0", "preHandle");

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {
        log.info("postHandle");
        streamBridge.send("log-in-0", "postHandle");
    }
}
