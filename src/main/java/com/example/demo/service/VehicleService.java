package com.example.demo.service;

import com.example.demo.Dto.CreateOwnerDto;
import com.example.demo.Dto.CreateVehicleDto;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final OwnerService ownerService;
    private final ArchiveService archiveService;

    public Vehicle registerVehicle(CreateVehicleDto vehicleDto) {
        CreateOwnerDto ownerDto = CreateOwnerDto.builder()
                .newOwnerCode(vehicleDto.getOwnerCode())
                .newLegalName(vehicleDto.getOwnerLegalName())
                .newOwnerName(vehicleDto.getOwnerName())
                .newOwnerSurname(vehicleDto.getOwnerSurname())
                .build();
        Owner owner = ownerService.createOwner(ownerDto);
        Vehicle vehicle = Vehicle.builder()
                .vin("test")
                .make(vehicleDto.getMake())
                .year(vehicleDto.getYear())
                .model(vehicleDto.getModel())
                .plateNo(vehicleDto.getPlateNo())
                .isActive(true)
                .owner(owner)
                .build();
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle getVehicleById(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("vehicle not found "));
    }

    public void deleteVehicle (UUID vehicleId){
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
        archiveService.addToArchive(vehicle);
    }
}
