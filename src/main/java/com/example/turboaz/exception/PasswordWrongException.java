package com.example.turboaz.exception;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(String message) {
        super(message);
    }
}