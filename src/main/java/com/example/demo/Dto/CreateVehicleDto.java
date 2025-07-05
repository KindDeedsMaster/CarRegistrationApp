package com.example.demo.Dto;

import lombok.Builder;
import lombok.Value;

import java.time.Year;

@Value
@Builder
public class CreateVehicleDto {
    String plateNo;
    String make;
    String model;
    Year year;
    String ownerName;
    String ownerSurname;
    String ownerLegalName;
    String ownerCode;
}
