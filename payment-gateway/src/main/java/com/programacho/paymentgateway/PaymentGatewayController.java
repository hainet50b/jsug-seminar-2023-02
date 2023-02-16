package com.programacho.paymentgateway;

import com.programacho.paymentgateway.credit.CreditAuthorizeRequest;
import com.programacho.paymentgateway.credit.CreditAuthorizeResponse;
import com.programacho.paymentgateway.credit.CreditService;
import com.programacho.paymentgateway.qr.QrCreateCodeRequest;
import com.programacho.paymentgateway.qr.QrCreateCodeResponse;
import com.programacho.paymentgateway.qr.QrService;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    public PaymentGatewayCreditAuthorizeResponse creditAuthorize(@RequestBody PaymentGatewayCreditAuthorizeRequest request) {
        setContext(
                "クレジットカード与信",
                "/credit/authorize",
                request.user()
        );

        CreditAuthorizeResponse response = creditService.authorize(new CreditAuthorizeRequest(
                "payment-gateway",
                request.token(),
                request.amount()
        ));

        paymentGatewayService.commitTransaction();

        return new PaymentGatewayCreditAuthorizeResponse(
                response.result(),
                UUID.randomUUID().toString(),
                response.errorCode()
        );
    }

    @PostMapping("/qr/create-code")
    public PaymentGatewayQrCreateCodeResponse qrCreateCode(@RequestBody PaymentGatewayQrCreateCodeRequest request) {
        setContext(
                "QRコード発行",
                "/qr/create-code",
                request.user()
        );

        QrCreateCodeResponse response = qrService.createCode(new QrCreateCodeRequest(
                "payment-gateway",
                request.amount()
        ));

        paymentGatewayService.commitTransaction();

        return new PaymentGatewayQrCreateCodeResponse(
                response.result(),
                UUID.randomUUID().toString(),
                response.url(),
                response.errorCode()
        );
    }

    private void setContext(String function, String endpoint, String user) {
        MDC.put("labels.function", function);
        MDC.put("labels.endpoint", endpoint);
        MDC.put("labels.user", user);
    }
}
