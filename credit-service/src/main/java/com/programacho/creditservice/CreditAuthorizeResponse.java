package com.programacho.creditservice;

public record CreditAuthorizeResponse(
        String result,
        String id,
        String errorCode) {
}
