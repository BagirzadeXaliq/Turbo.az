package com.example.turboaz.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

// car, entity, image, review, transaction, user