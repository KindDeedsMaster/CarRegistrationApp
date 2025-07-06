package com.example.demo.exception;

public class VehicleHasNoOwnerException extends RuntimeException {
    public VehicleHasNoOwnerException(String message) {
        super(message);
    }
}
