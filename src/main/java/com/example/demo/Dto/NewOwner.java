package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
public class NewOwner {
    String newOwnerCode;
    String newOwnerName;
    String newOwnerSurname;
    String newLegalName;
}
