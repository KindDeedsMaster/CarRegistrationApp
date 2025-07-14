package com.example.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.Year;

@Value
@Builder
public class NewVehicleDto {
    @NotBlank(message = "Plate number cannot be null")
    String plateNo;
    @NotBlank(message = "Make cannot be blank")
    String make;
    @NotBlank(message = "Model cannot be blank")
    String model;
    Year year;
    String ownerName;
    String ownerSurname;
    String ownerLegalName;
    @NotBlank(message = "Owner code cannot be blank")
    String ownerCode;
}
