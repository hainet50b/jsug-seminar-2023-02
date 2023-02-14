package com.programacho.paymentgateway;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentGatewayController {

    private final CreditService creditService;

    private final QrService qrService;

    public PaymentGatewayController(CreditService creditService, QrService qrService) {
        this.creditService = creditService;
        this.qrService = qrService;
    }

    @PostMapping("/credit/authorize")
    public String creditAuthorize() {
        return "ok";
    }

    @PostMapping("/qr/create-code")
    public String qrCreateCode() {
        return "ok";
    }
}
