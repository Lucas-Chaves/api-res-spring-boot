package com.lucas.demorparkapi.exception;

public class USerNameUniqueViolationException extends RuntimeException {
    public USerNameUniqueViolationException(String message) {
        super(message);
    }
}
