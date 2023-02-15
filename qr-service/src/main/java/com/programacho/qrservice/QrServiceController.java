package com.programacho.qrservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrServiceController {

    private final Logger log = LoggerFactory.getLogger(QrServiceController.class);

    @PostMapping("/create-code")
    public QrCreateCodeResponse createCode(@RequestBody QrCreateCodeRequest request) {
        log.info("QRコードを発行しました。");

        return new QrCreateCodeResponse("ok", null);
    }
}
