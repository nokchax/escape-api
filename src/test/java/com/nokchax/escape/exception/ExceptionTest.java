package com.nokchax.escape.exception;

class ExceptionTest {

    void runtimeExceptionTest() {
        throw new RuntimeException("hi");
    }

    void exceptionTest() throws Exception {
        throw new Exception("hi");
    }

}