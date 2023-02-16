package com.programacho.qrservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class QrServiceController {

    private final Logger log = LoggerFactory.getLogger(QrServiceController.class);

    @PostMapping("/create-code")
    public QrCreateCodeResponse createCode(@RequestBody QrCreateCodeRequest request) {
        setContext(
                "QRコード発行",
                "/create-code",
                request.user()
        );

        log.info("QRコードを発行しました。");

        return new QrCreateCodeResponse(
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
