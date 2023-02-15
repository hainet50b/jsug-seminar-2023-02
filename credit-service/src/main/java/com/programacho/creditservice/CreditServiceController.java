package com.programacho.creditservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditServiceController {

    private final Logger log = LoggerFactory.getLogger(CreditServiceController.class);

    @PostMapping("/authorize")
    public CreditAuthorizeResponse authorize(@RequestBody CreditAuthorizeRequest request) {
        log.info("クレジットカードの与信を取得しました。");

        return new CreditAuthorizeResponse("ok", null);
    }
}
