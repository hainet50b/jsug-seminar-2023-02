package com.programacho.paymentgateway;

public record PaymentGatewayCreditAuthorizeResponse(
        String result,
        String id,
        String errorCode) {
}
