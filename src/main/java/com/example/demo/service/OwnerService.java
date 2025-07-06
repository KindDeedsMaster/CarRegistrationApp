package com.example.demo.service;

import com.example.demo.Dto.NewOwner;
import com.example.demo.entity.Owner;
import com.example.demo.enums.OwnerType;
import com.example.demo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner createOwner(NewOwner newOwner) {
        Owner owner = Owner.builder()
                .ownerType(newOwner.getNewOwnerCode().length() == 9 ? OwnerType.LEGAL : OwnerType.PRIVATE)
                .name(newOwner.getNewOwnerName())
                .surname(newOwner.getNewOwnerSurname())
                .legalName(newOwner.getNewLegalName())
                .ownerCode(newOwner.getNewOwnerCode())
                .build();
        ownerRepository.save(owner);
        return owner;
    }

    public Owner getNewOwner(NewOwner newOwner) {
        return ownerRepository.findByOwnerCode(newOwner.getNewOwnerCode())
                .orElseGet(() -> createOwner(newOwner));
    }
}
