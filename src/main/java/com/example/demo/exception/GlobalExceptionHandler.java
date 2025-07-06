package com.example.demo.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException exception) {
        ProblemDetail response = ProblemDetail.forStatus(404);
        response.setTitle("Entity Not Found");
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(PlateInUseException.class)
    public ProblemDetail handlePlateInUse(PlateInUseException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Plate in use already");
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(VehicleHasNoOwnerException.class)
    public ProblemDetail handleHasNoOwner(VehicleHasNoOwnerException exception) {
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Vehicle has no owner");
        response.setDetail(exception.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Invalid data");
        response.setDetail("One or more fields have validation errors.");
        response.setProperty("errors", errors);
        return response;
    }
}
