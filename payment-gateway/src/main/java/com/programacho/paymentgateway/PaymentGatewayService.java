package com.programacho.paymentgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentGatewayService {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayService.class);

    public void commitTransaction() {
        if (MDC.get("service.node.name").equals("node-2")) {
            Random random = new Random();

            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(3_000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        log.info("決済トランザクションの確定が完了しました。");
    }
}
