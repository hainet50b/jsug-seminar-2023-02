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

    public QrCreateCodeResponse createCode(QrCreateCodeRequest request) {
        QrCreateCodeResponse response = restTemplate.postForObject(
                "http://localhost:8091/create-code",
                request,
                QrCreateCodeResponse.class
        );

        log.info(response.toString());

        return response;
    }
}
