package com.programacho.paymentgateway.credit;

import com.programacho.paymentgateway.EnvironmentSimulator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class CreditMDCHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("service.node.name", EnvironmentSimulator.node());

        return true;
    }
}
