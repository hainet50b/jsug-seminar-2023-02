package com.programacho.paymentgateway;

public record CreditAuthorizeRequest(String token, String user, Integer amount) {
}
