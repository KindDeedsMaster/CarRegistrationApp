package com.example.demo.service;

import com.example.demo.Dto.CreateVehicleDto;
import com.example.demo.Dto.NewOwner;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Vehicle;
import com.example.demo.exception.VehicleHasNoOwnerException;
import com.example.demo.exception.PlateInUseException;
import com.example.demo.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final OwnerService ownerService;
    private final ArchiveService archiveService;

    public Vehicle registerVehicle(CreateVehicleDto vehicleDto) {
        if (vehicleRepository.existsByPlateNo(vehicleDto.getPlateNo())){
            throw new PlateInUseException("Transporto priemonė su šiais numeriais jau egzistuoja");
        }
        NewOwner newOwner = NewOwner.builder()
                .newOwnerCode(vehicleDto.getOwnerCode())
                .newLegalName(vehicleDto.getOwnerLegalName())
                .newOwnerName(vehicleDto.getOwnerName())
                .newOwnerSurname(vehicleDto.getOwnerSurname())
                .build();
        Owner owner = ownerService.getNewOwner(newOwner);
        Vehicle vehicle = Vehicle.builder()
//                .vin(UUID.randomUUID())
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
        archiveService.addToArchive(vehicle);
        vehicle.setPlateNo("");
        vehicle.setActive(false);
        vehicle.setOwner(null);
        vehicleRepository.save(vehicle);
    }

    public Page<Vehicle> getAllVehicles (Pageable pageable){
        return vehicleRepository.findAll(pageable);
    }

    public Vehicle changeOwner (UUID vehicleId, NewOwner newOwner){
        Vehicle vehicle = getVehicleById(vehicleId);
        if (vehicle.getOwner() == null){
            throw new VehicleHasNoOwnerException("Vehicle has no owner, so can't be transferred");
        }
        Owner owner = ownerService.getNewOwner(newOwner);
        archiveService.addToArchive(vehicle);
        vehicle.setOwner(owner);
        vehicle.setActive(true);
        vehicle.setRegistrationDate(LocalDateTime.now());
        return vehicleRepository.save(vehicle);
    }
}
