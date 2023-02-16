package com.programacho.paymentgateway.qr;

public record QrCreateCodeResponse(
        String result,
        String id,
        String errorCode) {
}
