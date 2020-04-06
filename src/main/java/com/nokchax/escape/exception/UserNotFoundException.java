package com.nokchax.escape.exception;

public class UserNotFoundException extends EscapeApiException {

    public UserNotFoundException(String userId) {
        super("User not found : " + userId);
    }
}
