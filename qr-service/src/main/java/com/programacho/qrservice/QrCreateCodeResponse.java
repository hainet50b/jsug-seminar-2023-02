package com.programacho.qrservice;

public record QrCreateCodeResponse(
        String result,
        String id,
        String errorCode) {
}
