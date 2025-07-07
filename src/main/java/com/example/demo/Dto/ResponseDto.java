package com.example.demo.Dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ResponseDto {
    String message;
    UUID vehicleId;
}
