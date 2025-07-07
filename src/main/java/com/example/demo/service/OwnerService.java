package com.example.demo.service;

import com.example.demo.Dto.NewOwnerDto;
import com.example.demo.entity.Owner;
import com.example.demo.enums.OwnerType;
import com.example.demo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    @Transactional
    public Owner createOwner(NewOwnerDto newOwnerDto) {
        Owner owner = Owner.builder()
                .ownerType(newOwnerDto.getNewOwnerCode().length() == 9 ? OwnerType.LEGAL : OwnerType.PRIVATE)
                .name(newOwnerDto.getNewOwnerName())
                .surname(newOwnerDto.getNewOwnerSurname())
                .legalName(newOwnerDto.getNewLegalName())
                .ownerCode(newOwnerDto.getNewOwnerCode())
                .build();
        ownerRepository.save(owner);
        return owner;
    }

    public Owner getNewOwner(NewOwnerDto newOwnerDto) {
        return ownerRepository.findByOwnerCode(newOwnerDto.getNewOwnerCode())
                .orElseGet(() -> createOwner(newOwnerDto));
    }
}
