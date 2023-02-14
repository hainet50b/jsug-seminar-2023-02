package com.programacho.paymentgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QrService {

    private final Logger log = LoggerFactory.getLogger(QrService.class);

    private final RestTemplate restTemplate;

    public QrService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createCode() {
        String response = restTemplate.postForObject(
                "http://localhost:8091/create-code",
                null,
                String.class
        );

        log.info(response);

        return response;
    }
}
