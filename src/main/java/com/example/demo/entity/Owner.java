package com.example.demo.entity;

import com.example.demo.enums.OwnerType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "owner_code")
    private String ownerCode;

    @Column(name = "type")
    private OwnerType ownerType;






}
