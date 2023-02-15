package com.programacho.qrservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrServiceController {

    @PostMapping("/create-code")
    public QrCreateCodeResponse createCode(@RequestBody QrCreateCodeRequest request) {
        return new QrCreateCodeResponse("ok", null);
    }
}
