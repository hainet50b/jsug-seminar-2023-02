package com.programacho.paymentgateway;

public record LogIngesterPayload(String application, String type, String direction, String body) {
}
