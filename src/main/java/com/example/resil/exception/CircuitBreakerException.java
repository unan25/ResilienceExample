package com.example.resil.exception;

public class CircuitBreakerException extends RuntimeException {
    public CircuitBreakerException(String msg){
        super(msg);
    }
}
