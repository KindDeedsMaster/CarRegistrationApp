package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Column (name = "plate_no")
    private String plateNo;

    @Column (name = "registered_at")
    private LocalDateTime registeredAt;

    @Column (name = "registration_ended_at")
    private LocalDateTime registrationEndedAt;

    @PrePersist
    public void onCreate(){
        registrationEndedAt = LocalDateTime.now();
    }





}
