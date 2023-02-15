package com.programacho.paymentgateway;

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
        CreditAuthorizeResponse response = restTemplate.postForObject(
                "http://localhost:8090/authorize",
                request,
                CreditAuthorizeResponse.class
        );

        log.info(response.toString());

        return response;
    }
}
