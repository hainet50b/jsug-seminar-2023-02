package com.programacho.paymentgateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LogHandlerInterceptor logHandlerInterceptor;

    public WebMvcConfig(LogHandlerInterceptor logHandlerInterceptor) {
        this.logHandlerInterceptor = logHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logHandlerInterceptor);
    }
}
