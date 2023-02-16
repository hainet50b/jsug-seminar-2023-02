package com.programacho.paymentgateway.credit;

public record CreditAuthorizeRequest(
        String user,
        String token,
        Integer amount) {
}
