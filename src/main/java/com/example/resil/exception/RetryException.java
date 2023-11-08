package com.example.resil.exception;

public class RetryException extends RuntimeException {

    public RetryException(String msg) {
        super(msg);
    }
}
