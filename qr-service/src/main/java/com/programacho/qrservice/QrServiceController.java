package com.programacho.qrservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
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

        Random random = new Random();
        int i = random.nextInt(100);

        if (i <= 98) {
            String result = "ok";
            String paymentId = UUID.randomUUID().toString();
            String url = "https://programacho.com/" + paymentId;

            MDC.put("labels.payment.result", result);
            MDC.put("labels.payment.id", paymentId);
            MDC.put("labels.payment.qr.url", url);

            log.info("QRコードを発行しました。");

            return new QrCreateCodeResponse(
                    result,
                    paymentId,
                    url,
                    null
            );
        } else {
            String result = "ng";
            String paymentId = UUID.randomUUID().toString();
            String errorCode = "INVALID_PAYMENT";

            MDC.put("labels.payment.result", result);
            MDC.put("labels.payment.id", paymentId);
            MDC.put("labels.payment.error-code", errorCode);

            log.info("残高不足のためQRコードの発行に失敗しました。");

            return new QrCreateCodeResponse(
                    result,
                    paymentId,
                    null,
                    errorCode
            );
        }
    }

    private void setContext(String function, String endpoint, String user) {
        MDC.put("labels.function", function);
        MDC.put("labels.endpoint", endpoint);
        MDC.put("labels.user", user);
    }
}
