package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
public class VehicleHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

//    @Column(name = "owner_id")
//    private Owner owner;
//
//    @Column(name = "vehicle_id")
//    private Vehicle vehicle;

    @Column (name = "plate_no")
    private String plateNo;

    @Column (name = "registered_at")
    private LocalDateTime registeredAt;

    @Column (name = "registration_ended_at")
    private LocalDateTime registrationEndedAt;

    @PrePersist
    public void onCreate(){
        registrationEndedAt = LocalDateTime.now();
//        registeredAt = vehicle.getRegistrationDate();
//        plateNo = vehicle.getPlateNo();
    }





}
