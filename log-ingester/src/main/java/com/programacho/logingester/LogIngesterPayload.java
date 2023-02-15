package com.programacho.logingester;

public record LogIngesterPayload(String application, String type, String direction, String body) {
}
