package com.programacho.paymentgateway.credit;

public record CreditAuthorizeRequest(String token, String user, Integer amount) {
}
