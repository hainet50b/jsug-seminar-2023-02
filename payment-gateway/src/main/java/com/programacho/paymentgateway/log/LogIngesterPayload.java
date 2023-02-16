package com.programacho.paymentgateway.log;

public record LogIngesterPayload(String application, String type, String direction, String body) {
}
