package com.finance.api.exception;

public class ApiRequestExceptionId extends RuntimeException {
    public ApiRequestExceptionId(String message) {
        super(message);
    }
}
