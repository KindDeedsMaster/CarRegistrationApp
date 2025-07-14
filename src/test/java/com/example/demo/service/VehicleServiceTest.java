package com.example.demo.service;

import com.example.demo.Dto.NewOwnerDto;
import com.example.demo.Dto.NewVehicleDto;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Vehicle;
import com.example.demo.enums.OwnerType;
import com.example.demo.repository.VehicleRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ArchiveService archiveService;
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private VehicleService vehicleService;


    @Test
    public void VehicleService_RegisterVehicle_ReturnsVehicle() {
        UUID uuid = UUID.randomUUID();
        NewVehicleDto newVehicleDto = NewVehicleDto.builder()
                .plateNo("ABC123")
                .make("BMW")
                .model("E34")
                .ownerCode("123456789")
                .ownerLegalName("UAB")
                .year(Year.of(1995))
                .build();
        Owner mockOwner = Owner.builder()
                .id(uuid)
                .ownerCode("123456789")
                .legalName("UAB")
                .ownerType(OwnerType.LEGAL)
                .name("qq")
                .surname("qq")
                .build();
        Vehicle vehicle = Vehicle.builder()
                .id(uuid)
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .owner(mockOwner)
                .build();

        when(vehicleRepository.existsByPlateNo("ABC123")).thenReturn(false);
        when(ownerService.getNewOwner(any(NewOwnerDto.class))).thenReturn(mockOwner);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle registeredVehicle = vehicleService.registerVehicle(newVehicleDto);
        Assertions.assertThat(registeredVehicle).isNotNull();
        Assertions.assertThat(registeredVehicle.getModel()).isEqualTo("E34");
    }

    @Test
    public void VehicleService_getVehicleById_ReturnsVehicle() {
        UUID uuid = UUID.randomUUID();
        Owner mockOwner = Owner.builder()
                .ownerCode("123456789")
                .legalName("UAB")
                .ownerType(OwnerType.LEGAL)
                .name("qq")
                .surname("qq")
                .build();
        Vehicle vehicle = Vehicle.builder()
                .id(uuid)
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .owner(mockOwner)
                .build();

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.ofNullable(vehicle));

        Vehicle savedVehicle = vehicleService.getVehicleById(uuid);

        Assertions.assertThat(savedVehicle).isNotNull();
    }

    @Test
    public void VehicleService_deleteVehicle_VehicleActiveFalse() {
        UUID uuid = UUID.randomUUID();
        Owner mockOwner = Owner.builder()
                .ownerCode("123456789")
                .legalName("UAB")
                .ownerType(OwnerType.LEGAL)
                .name("qq")
                .surname("qq")
                .build();
        Vehicle vehicle = Vehicle.builder()
                .id(uuid)
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .owner(mockOwner)
                .build();

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(uuid);
        verify(archiveService).addToArchive(vehicle);
        Assertions.assertThat(vehicle.getPlateNo()).isEmpty();
        Assertions.assertThat(vehicle.isActive()).isFalse();
        Assertions.assertThat(vehicle.getOwner()).isNull();
    }

    @Test
    public void VehicleService_ChangeOwner_UpdatesOwnerAndArchivesVehicle() {
        UUID uuid = UUID.randomUUID();

        Owner oldOwner = Owner.builder()
                .ownerCode("123456789")
                .legalName("Old UAB")
                .ownerType(OwnerType.LEGAL)
                .name(null)
                .surname(null)
                .build();

        NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                .newOwnerCode("987654321")
                .newLegalName("New UAB")
                .newOwnerName("New")
                .newOwnerSurname("Owner")
                .build();

        Owner newOwner = Owner.builder()
                .id(uuid)
                .ownerCode("987654321")
                .legalName("New UAB")
                .ownerType(OwnerType.LEGAL)
                .name("New")
                .surname("Owner")
                .build();

        Vehicle vehicle = Vehicle.builder()
                .id(uuid)
                .plateNo("ABC123")
                .isActive(true)
                .owner(oldOwner)
                .registrationDate(LocalDateTime.now().minusYears(1))
                .build();

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));
        when(ownerService.getNewOwner(newOwnerDto)).thenReturn(newOwner);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle updatedVehicle = vehicleService.changeOwner(uuid, newOwnerDto);

        Assertions.assertThat(updatedVehicle.getOwner()).isEqualTo(newOwner);
        Assertions.assertThat(updatedVehicle.isActive()).isTrue();
        Assertions.assertThat(updatedVehicle.getRegistrationDate()).isNotNull();
    }
}