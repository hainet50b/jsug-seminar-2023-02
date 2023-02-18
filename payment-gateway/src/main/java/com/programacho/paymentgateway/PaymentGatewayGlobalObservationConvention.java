package com.programacho.paymentgateway;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.observation.GlobalObservationConvention;
import io.micrometer.observation.Observation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentGatewayGlobalObservationConvention implements GlobalObservationConvention<Observation.Context> {

    @Override
    public boolean supportsContext(@NotNull Observation.Context context) {
        return true;
    }

    @Override
    @NotNull
    public KeyValues getLowCardinalityKeyValues(Observation.Context context) {
        List<String> keys = List.of(
                // General
                "service.node.name",
                "labels.function",
                "labels.endpoint",
                // payment-gateway
                "labels.payment-gateway.result",
                "labels.payment-gateway.error-code",
                // payment
                "labels.payment.result",
                "labels.payment.error-code"
        );

        MDC.getCopyOfContextMap().forEach((k, v) -> {
            if (keys.contains(k)) {
                context.addLowCardinalityKeyValue(KeyValue.of(k, v));
            }
        });

        return context.getLowCardinalityKeyValues();
    }

    @Override
    @NotNull
    public KeyValues getHighCardinalityKeyValues(Observation.Context context) {
        List<String> keys = List.of(
                // General
                "labels.user",
                // payment-gateway
                "labels.payment-gateway.id",
                // payment
                "labels.payment.id",
                "labels.payment.token",
                "labels.payment.qr.url"
        );

        MDC.getCopyOfContextMap().forEach((k, v) -> {
            if (keys.contains(k)) {
                context.addHighCardinalityKeyValue(KeyValue.of(k, v));
            }
        });

        return context.getHighCardinalityKeyValues();
    }
}
