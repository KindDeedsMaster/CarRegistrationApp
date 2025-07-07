package com.example.demo.controller;

import com.example.demo.Dto.NewVehicleDto;
import com.example.demo.Dto.NewOwner;
import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Vehicle createVehicle(@Valid @RequestBody NewVehicleDto newVehicle) {
        System.out.println(newVehicle);
        return vehicleService.registerVehicle(newVehicle);
    }

    @GetMapping(path = "/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle getVehicle(@PathVariable UUID vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }

    @DeleteMapping(path = "/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable UUID vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }

    @PostMapping(path = "/{vehicleId}/transfer-owner")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle changeOwner (@PathVariable UUID vehicleId,
                                @Valid @RequestBody NewOwner newOwner){
        return vehicleService.changeOwner(vehicleId, newOwner);
    }

    @GetMapping
    public Page<Vehicle> getVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return vehicleService.getAllVehicles(pageable);
    }

}
