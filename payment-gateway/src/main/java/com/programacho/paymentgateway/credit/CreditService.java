package com.programacho.paymentgateway.credit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditService {

    private final Logger log = LoggerFactory.getLogger(CreditService.class);

    private final RestTemplate restTemplate;

    public CreditService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CreditAuthorizeResponse authorize(CreditAuthorizeRequest request) {
        // TODO: 機能などを含む構造化ログに変更する。
        log.info("credit-serviceに通信を開始します。");

        CreditAuthorizeResponse response = restTemplate.postForObject(
                "http://localhost:8090/authorize",
                request,
                CreditAuthorizeResponse.class
        );

        // TODO: 機能などを含む構造化ログに変更する。
        log.info("credit-serviceへの通信が完了しました。");

        return response;
    }
}
