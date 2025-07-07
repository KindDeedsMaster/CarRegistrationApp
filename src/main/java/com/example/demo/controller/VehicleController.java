package com.example.demo.controller;

import com.example.demo.Dto.NewVehicleDto;
import com.example.demo.Dto.NewOwnerDto;
import com.example.demo.Dto.ResponseDto;
import com.example.demo.Dto.ResponseVehicleDto;
import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createVehicle(@Valid @RequestBody NewVehicleDto newVehicle) {
        System.out.println(newVehicle);
        Vehicle vehicle = vehicleService.registerVehicle(newVehicle);
        return ResponseDto.builder()
                .message("Transporto priemonė sėkmingai užregistruota")
                .vehicleId(vehicle.getId())
                .build();
    }

    @GetMapping(path = "/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseVehicleDto getVehicle(@PathVariable UUID vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseVehicleDto.builder()
                .vehicleId(vehicle.getId())
                .plateNo(vehicle.getPlateNo())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .ownerName(vehicle.getOwner().getName())
                .ownerSurname(vehicle.getOwner().getSurname())
                .ownerLegalName(vehicle.getOwner().getLegalName())
                .ownerCode(vehicle.getOwner().getOwnerCode())
                .build();
    }

    @DeleteMapping(path = "/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteVehicle(@PathVariable UUID vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseDto.builder()
                .message("Transporto priemonė sėkmingai pašalinta")
                .vehicleId(vehicleId)
                .build();
    }

    @PostMapping(path = "/{vehicleId}/transfer-owner")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto changeOwner(@PathVariable UUID vehicleId,
                               @Valid @RequestBody NewOwnerDto newOwnerDto) {
        Vehicle vehicle = vehicleService.changeOwner(vehicleId, newOwnerDto);
        return ResponseDto.builder()
                .message("Transporto priemonės savininkystė sėkmingai perleista")
                .vehicleId(vehicle.getId())
                .build();
    }

    @GetMapping
    public Page<Vehicle> getVehicles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return vehicleService.getAllVehicles(pageable);
    }

}
