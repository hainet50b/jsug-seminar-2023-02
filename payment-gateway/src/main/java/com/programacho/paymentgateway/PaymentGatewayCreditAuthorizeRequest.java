package com.programacho.paymentgateway;

public record PaymentGatewayCreditAuthorizeRequest(
        String user,
        String token,
        Integer amount) {
}
