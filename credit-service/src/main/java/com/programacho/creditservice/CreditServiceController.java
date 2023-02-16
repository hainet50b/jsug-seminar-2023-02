package com.programacho.creditservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        log.info("クレジットカードの与信を取得しました。");

        return new CreditAuthorizeResponse(
                "ok",
                UUID.randomUUID().toString(),
                null
        );
    }

    private void setContext(String function, String endpoint, String user) {
        MDC.put("labels.function", function);
        MDC.put("labels.endpoint", endpoint);
        MDC.put("labels.user", user);
    }
}
