package com.example.demo.service;

import com.example.demo.entity.Archive;
import com.example.demo.entity.Vehicle;
import com.example.demo.exception.VehicleHasNoOwnerException;
import com.example.demo.repository.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveService {
    private final ArchiveRepository archiveRepository;

        public void addToArchive(Vehicle vehicle) {
            if (vehicle.getOwner() == null){
                throw new VehicleHasNoOwnerException("can't save vehicle to archive without owner");
            }
        Archive archive = Archive.builder()
                .vehicleId(vehicle.getId())
                .ownerId(vehicle.getOwner().getId())
                .plateNo(vehicle.getPlateNo())
                .registeredAt(vehicle.getRegistrationDate())
                .build();
        archiveRepository.save(archive);
    }
}
