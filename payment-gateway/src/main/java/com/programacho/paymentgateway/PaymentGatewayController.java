package com.programacho.paymentgateway;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentGatewayController {

    private final CreditService creditService;

    private final QrService qrService;

    private final PaymentGatewayService paymentGatewayService;

    public PaymentGatewayController(
            CreditService creditService,
            QrService qrService,
            PaymentGatewayService paymentGatewayService) {
        this.creditService = creditService;
        this.qrService = qrService;
        this.paymentGatewayService = paymentGatewayService;
    }

    @PostMapping("/credit/authorize")
    public String creditAuthorize() {
        String result = creditService.authorize();

        paymentGatewayService.commitTransaction();

        return result;
    }

    @PostMapping("/qr/create-code")
    public String qrCreateCode() {
        String result = qrService.createCode();

        paymentGatewayService.commitTransaction();

        return result;
    }
}
