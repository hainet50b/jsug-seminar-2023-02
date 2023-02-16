package com.programacho.creditservice;

public record CreditAuthorizeRequest(
        String user,
        String token,
        Integer amount) {
}
