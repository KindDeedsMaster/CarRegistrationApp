package com.example.demo.service;

import com.example.demo.Dto.CreateOwnerDto;
import com.example.demo.entity.Owner;
import com.example.demo.enums.OwnerType;
import com.example.demo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner createOwner(CreateOwnerDto newOwner) {
//        if (newOwner.getNewLegalName().isBlank() && newOwner.getNewOwnerCode().length() == 9){
//            OwnerType ownerType = OwnerType.LEGAL;
//        }
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

    private boolean isLegal(String ownerCode) {
        return ownerCode.length() == 9;
    }
}
