package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "vin")
    private String vin;

    @Column(name = "plate_no")
    private String plateNo;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacture_year")
    private int year;

    @Column(name = "is_active")
    private boolean isActive;

//    @Column(name = "owner_id")
//    private Owner currentOwner;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @PrePersist
    public void onCreate(){
        registrationDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        registrationDate = LocalDateTime.now();
    }
}
