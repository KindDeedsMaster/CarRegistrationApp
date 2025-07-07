package com.example.demo.service;

import com.example.demo.entity.Archive;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArchiveService {
    private final ArchiveRepository archiveRepository;

    @Transactional
    public void addToArchive(Vehicle vehicle) {
        Archive archive = Archive.builder()
                .vehicleId(vehicle.getId())
                .ownerId(vehicle.getOwner().getId())
                .plateNo(vehicle.getPlateNo())
                .registeredAt(vehicle.getRegistrationDate())
                .build();
        archiveRepository.save(archive);
    }
}
