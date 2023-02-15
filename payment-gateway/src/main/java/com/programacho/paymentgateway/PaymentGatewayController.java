package com.programacho.paymentgateway;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public PaymentGatewayResponse creditAuthorize(@RequestBody CreditAuthorizeRequest request) {
        CreditAuthorizeResponse response = creditService.authorize(request);

        paymentGatewayService.commitTransaction();

        return new PaymentGatewayResponse(
                response.result(),
                response.errorCode()
        );
    }

    @PostMapping("/qr/create-code")
    public PaymentGatewayResponse qrCreateCode(@RequestBody QrCreateCodeRequest request) {
        QrCreateCodeResponse response = qrService.createCode(request);

        paymentGatewayService.commitTransaction();

        return new PaymentGatewayResponse(
                response.result(),
                response.errorCode()
        );
    }
}
