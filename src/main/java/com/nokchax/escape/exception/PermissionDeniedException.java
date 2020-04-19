package com.nokchax.escape.exception;

public class PermissionDeniedException extends EscapeApiException {

    public PermissionDeniedException() {
        super("Permission denied");
    }
}
