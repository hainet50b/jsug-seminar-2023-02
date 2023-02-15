package com.programacho.paymentgateway;

import org.slf4j.MDC;

import java.util.UUID;

public class StructuredLoggingConvention {

    private StructuredLoggingConvention() {
    }

    public static void credit() {
        MDC.put("labels.user", UUID.randomUUID().toString());
    }

    public static void qr() {
        MDC.put("labels.user", UUID.randomUUID().toString());
    }
}
