package com.programacho.paymentgateway;

public record PaymentGatewayResponse(
        String result,
        String id,
        String errorCode) {
}
