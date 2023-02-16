package com.programacho.creditservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
public class CreditServiceController {

    private final Logger log = LoggerFactory.getLogger(CreditServiceController.class);

    @PostMapping("/authorize")
    public CreditAuthorizeResponse authorize(@RequestBody CreditAuthorizeRequest request) {
        setContext(
                "クレジットカード与信",
                "/authorize",
                request.user()
        );

        Random random = new Random();
        int i = random.nextInt(100);

        if (i <= 90) {
            String result = "ok";
            String paymentId = UUID.randomUUID().toString();

            MDC.put("labels.payment.result", result);
            MDC.put("labels.payment.id", paymentId);

            log.info("クレジットカードの与信を取得しました。");

            return new CreditAuthorizeResponse(
                    result,
                    paymentId,
                    null
            );
        } else {
            String result = "ng";
            String paymentId = UUID.randomUUID().toString();
            String errorCode = "INVALID_PAYMENT";

            MDC.put("labels.payment.result", "ng");
            MDC.put("labels.payment.id", paymentId);
            MDC.put("labels.payment.error-code", errorCode);

            log.info("残高不足のためクレジットカードの与信の取得に失敗しました。");

            return new CreditAuthorizeResponse(
                    result,
                    paymentId,
                    errorCode
            );
        }
    }

    private void setContext(String function, String endpoint, String user) {
        MDC.put("labels.function", function);
        MDC.put("labels.endpoint", endpoint);
        MDC.put("labels.user", user);
    }
}
