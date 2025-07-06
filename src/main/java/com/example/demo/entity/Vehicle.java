package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "vin", unique = true)
    private UUID vin;

    @Column(name = "plate_no", unique = true)
    private String plateNo;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacture_year")
    private Year year;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @PrePersist
    public void onCreate(){
        registrationDate = LocalDateTime.now();
    }

//    @PreUpdate
//    public void onUpdate(){
//        registrationDate = LocalDateTime.now();
//    }
}
