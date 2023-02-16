package com.programacho.paymentgateway;

import com.programacho.paymentgateway.credit.CreditAuthorizeRequest;
import com.programacho.paymentgateway.credit.CreditAuthorizeResponse;
import com.programacho.paymentgateway.credit.CreditService;
import com.programacho.paymentgateway.qr.QrCreateCodeRequest;
import com.programacho.paymentgateway.qr.QrCreateCodeResponse;
import com.programacho.paymentgateway.qr.QrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentGatewayController {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayController.class);

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
        String paymentGatewayId = UUID.randomUUID().toString();
        MDC.put("labels.payment-gateway.id", paymentGatewayId);
        MDC.put("labels.payment.token", request.token());

        setContext(
                "クレジットカード与信",
                "/credit/authorize",
                request.user(),
                paymentGatewayId
        );

        if (!isValid(request.user())) {
            String result = "ng";
            String errorCode = "INVALID_USER";

            MDC.put("labels.payment-gateway.result", result);
            MDC.put("labels.payment-gateway.error-code", errorCode);

            log.info("許可されていないユーザーからのリクエストを遮断しました。");

            return new PaymentGatewayCreditAuthorizeResponse(
                    result,
                    paymentGatewayId,
                    errorCode
            );
        }

        CreditAuthorizeResponse response = creditService.authorize(new CreditAuthorizeRequest(
                "payment-gateway",
                request.token(),
                request.amount()
        ));

        MDC.put("labels.payment.result", response.result());
        MDC.put("labels.payment.id", response.id());
        MDC.put("labels.payment.error-code", response.errorCode());

        paymentGatewayService.commitTransaction();

        MDC.put("labels.payment-gateway.result", response.result());
        MDC.put("labels.payment-gateway.error-code", response.errorCode());

        return new PaymentGatewayCreditAuthorizeResponse(
                response.result(),
                paymentGatewayId,
                response.errorCode()
        );
    }

    @PostMapping("/qr/create-code")
    public PaymentGatewayQrCreateCodeResponse qrCreateCode(@RequestBody PaymentGatewayQrCreateCodeRequest request) {
        String paymentGatewayId = UUID.randomUUID().toString();

        setContext(
                "QRコード発行",
                "/qr/create-code",
                request.user(),
                paymentGatewayId
        );

        if (!isValid(request.user())) {
            String result = "ng";
            String errorCode = "INVALID_USER";

            MDC.put("labels.payment-gateway.result", result);
            MDC.put("labels.payment-gateway.error-code", errorCode);

            log.info("許可されていないユーザーからのリクエストを遮断しました。");

            return new PaymentGatewayQrCreateCodeResponse(
                    result,
                    paymentGatewayId,
                    null,
                    errorCode
            );
        }

        QrCreateCodeResponse response = qrService.createCode(new QrCreateCodeRequest(
                "payment-gateway",
                request.amount()
        ));

        MDC.put("labels.payment.result", response.result());
        MDC.put("labels.payment.id", response.id());
        MDC.put("labels.payment.qr.url", response.url());
        MDC.put("labels.payment.error-code", response.errorCode());

        paymentGatewayService.commitTransaction();

        MDC.put("labels.payment-gateway.result", response.result());
        MDC.put("labels.payment-gateway.error-code", response.errorCode());

        return new PaymentGatewayQrCreateCodeResponse(
                response.result(),
                paymentGatewayId,
                response.url(),
                response.errorCode()
        );
    }

    private void setContext(String function, String endpoint, String user, String id) {
        MDC.put("labels.function", function);
        MDC.put("labels.endpoint", endpoint);
        MDC.put("labels.user", user);
        MDC.put("labels.payment-gateway.id", id);
    }

    private boolean isValid(String user) {
        if (user.equals("b59fc898-9e53-4307-9c57-eca214072aa8")) {
            return false;
        }

        return true;
    }
}
