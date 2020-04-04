package com.nokchax.escape.exception;

public class InvalidTimeType extends EscapeApiException {
    public InvalidTimeType(String type) {
        super("Invalid time type : " + type);
    }
}
