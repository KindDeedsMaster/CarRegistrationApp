package com.example.demo.Dto;

import lombok.Builder;
import lombok.Value;

import java.time.Year;
import java.util.UUID;

@Value
@Builder
public class ResponseVehicleDto {
    UUID vehicleId;
    String plateNo;
    String make;
    String model;
    Year year;
    String ownerName;
    String ownerSurname;
    String ownerLegalName;
    String ownerCode;
}
