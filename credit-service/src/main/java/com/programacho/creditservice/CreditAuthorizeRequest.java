package com.programacho.creditservice;

public record CreditAuthorizeRequest(String token, String user, Integer amount) {
}
