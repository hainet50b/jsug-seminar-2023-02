package com.programacho.paymentgateway.credit;

public record CreditAuthorizeResponse(
        String result,
        String id,
        String errorCode) {
}
