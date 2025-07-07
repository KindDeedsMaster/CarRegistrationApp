package com.example.demo.Dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewOwnerDto {
    String newOwnerCode;
    String newOwnerName;
    String newOwnerSurname;
    String newLegalName;
}
