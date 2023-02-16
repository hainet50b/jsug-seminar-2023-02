package com.programacho.paymentgateway;

public record PaymentGatewayQrCreateCodeRequest(
        String user,
        Integer amount
) {
}
