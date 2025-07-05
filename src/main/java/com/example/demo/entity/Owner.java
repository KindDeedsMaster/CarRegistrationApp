package com.example.demo.entity;

import com.example.demo.enums.OwnerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "owner_code", unique = true)
    private String ownerCode;

    @Column(name = "type")
    @Enumerated (EnumType.STRING)
    private OwnerType ownerType;

    @Column(name = "vehicle")
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Vehicle> vehicles;
}
