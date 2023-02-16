package com.programacho.paymentgateway;

public record PaymentGatewayQrCreateCodeResponse(
        String result,
        String id,
        String url,
        String errorCode) {
}
