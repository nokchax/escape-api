package com.nokchax.escape.exception;

public class UnAuthorizedException extends EscapeApiException {
    public UnAuthorizedException() {
        super("Permission denied");
    }
}
