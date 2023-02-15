package com.programacho.paymentgateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LogHandlerInterceptor logHandlerInterceptor;

    private final CreditMDCHandlerInterceptor creditMdcHandlerInterceptor;

    private final QrMDCHandlerInterceptor qrMDCHandlerInterceptor;

    public WebMvcConfig(LogHandlerInterceptor logHandlerInterceptor) {
        this.logHandlerInterceptor = logHandlerInterceptor;
        this.creditMdcHandlerInterceptor = new CreditMDCHandlerInterceptor();
        this.qrMDCHandlerInterceptor = new QrMDCHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(creditMdcHandlerInterceptor)
                .addPathPatterns("/credit/**");

        registry
                .addInterceptor(qrMDCHandlerInterceptor)
                .addPathPatterns("/qr/**");

        registry
                .addInterceptor(logHandlerInterceptor);
    }
}
