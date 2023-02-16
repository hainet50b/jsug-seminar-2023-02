package com.programacho.paymentgateway;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.observation.GlobalObservationConvention;
import io.micrometer.observation.Observation;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayGlobalObservationConvention implements GlobalObservationConvention<Observation.Context> {

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    @Override
    public KeyValues getLowCardinalityKeyValues(Observation.Context context) {
        // General
        if (MDC.get("service.node.name") != null) context.addLowCardinalityKeyValue(KeyValue.of("service.node.name", MDC.get("service.node.name")));
        if (MDC.get("labels.function") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.function", MDC.get("labels.function")));
        if (MDC.get("labels.endpoint") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.endpoint", MDC.get("labels.endpoint")));

        // payment-gateway
        if (MDC.get("labels.payment-gateway.result") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment-gateway.result", MDC.get("service.node.name")));
        if (MDC.get("labels.payment-gateway.error-code") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment-gateway.error-code", MDC.get("labels.payment-gateway.error-code")));

        // payment
        if (MDC.get("labels.payment.result") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment.result", MDC.get("labels.payment.result")));
        if (MDC.get("labels.payment.error-code") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment.error-code", MDC.get("labels.payment.error-code")));

        return context.getLowCardinalityKeyValues();
    }

    @Override
    public KeyValues getHighCardinalityKeyValues(Observation.Context context) {
        if (MDC.get("labels.user") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.user", MDC.get("labels.user")));

        // payment-gateway
        if (MDC.get("labels.payment-gateway.id") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment-gateway.id", MDC.get("labels.payment-gateway.id")));

        // payment
        if (MDC.get("labels.payment.id") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment.id", MDC.get("labels.payment.id")));
        if (MDC.get("labels.payment.token") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment.token", MDC.get("labels.payment.token")));
        if (MDC.get("labels.payment.qr.url") != null) context.addLowCardinalityKeyValue(KeyValue.of("labels.payment.qr.url", MDC.get("labels.payment.qr.url")));

        return context.getHighCardinalityKeyValues();
    }
}
