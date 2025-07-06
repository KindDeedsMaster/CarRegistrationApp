package com.example.demo.exception;

public class PlateInUseException extends RuntimeException {
    public PlateInUseException(String message) {
        super(message);
    }
}
