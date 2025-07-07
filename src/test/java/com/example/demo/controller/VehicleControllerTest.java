package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import com.example.demo.Dto.NewOwnerDto;
import com.example.demo.Dto.NewVehicleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entity.Owner;
import com.example.demo.entity.Vehicle;
import com.example.demo.enums.OwnerType;
import com.example.demo.service.VehicleService;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void VehicleController_CreateVehicle_ReturnCreatedResponse() throws Exception {
        UUID uuid = UUID.randomUUID();

        NewVehicleDto newVehicleDto = NewVehicleDto.builder()
                .plateNo("ABC123")
                .make("BMW")
                .model("E34")
                .ownerCode("123456789")
                .ownerLegalName("UAB")
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
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .owner(mockOwner)
                .build();

        when(vehicleService.registerVehicle(any(NewVehicleDto.class))).thenReturn(vehicle);

        mockMvc.perform(post("/vehicles/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVehicleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Transporto priemonė sėkmingai užregistruota"))
                .andExpect(jsonPath("$.vehicleId").value(uuid.toString()));
    }

    @Test
    void VehicleController_DeleteVehicle_ReturnOkResponse() throws Exception {
        UUID uuid = UUID.randomUUID();

        doNothing().when(vehicleService).deleteVehicle(uuid);

        mockMvc.perform(delete("/vehicles/{vehicleId}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transporto priemonė sėkmingai pašalinta"))
                .andExpect(jsonPath("$.vehicleId").value(uuid.toString()));
    }

    @Test
    void VehicleController_ChangeOwner_ReturnOkResponse() throws Exception {
        UUID vehicleId = UUID.randomUUID();

        NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                .newOwnerCode("123456789")
                .newLegalName("UAB")
                .newOwnerName("Jonas")
                .newOwnerSurname("Jonaitis")
                .build();

        Owner newOwner = Owner.builder()
                .id(UUID.randomUUID())
                .ownerCode("123456789")
                .legalName("UAB")
                .name("Jonas")
                .surname("Jonaitis")
                .ownerType(OwnerType.LEGAL)
                .build();

        Vehicle updatedVehicle = Vehicle.builder()
                .id(vehicleId)
                .make("Audi")
                .model("A6")
                .plateNo("AAA111")
                .owner(newOwner)
                .isActive(true)
                .build();

        when(vehicleService.changeOwner(any(UUID.class), any(NewOwnerDto.class)))
                .thenReturn(updatedVehicle);

        mockMvc.perform(post("/vehicles/{vehicleId}/transfer-owner", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwnerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transporto priemonės savininkystė sėkmingai perleista"))
                .andExpect(jsonPath("$.vehicleId").value(vehicleId.toString()));
    }

    @Test
    void VehicleController_GetVehicles_ReturnsPagedList() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Vehicle v1 = Vehicle.builder()
                .id(uuid1)
                .plateNo("ABC123")
                .make("Toyota")
                .model("Corolla")
                .isActive(true)
                .build();

        Vehicle v2 = Vehicle.builder()
                .id(uuid2)
                .plateNo("ABC321")
                .make("BMW")
                .model("E34")
                .isActive(true)
                .build();

        List<Vehicle> vehicles = List.of(v1, v2);
        Pageable pageable = PageRequest.of(1, 2);
        Page<Vehicle> page = new PageImpl<>(vehicles, pageable, vehicles.size());

        when(vehicleService.getAllVehicles(pageable)).thenReturn(page);

        mockMvc.perform(get("/vehicles")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].plateNo").value("ABC123"))
                .andExpect(jsonPath("$.content[1].plateNo").value("ABC321"));
    }

    @Test
    void VehicleController_GetVehicleById_ReturnsVehicleDto() throws Exception {
        UUID vehicleId = UUID.randomUUID();

        Owner mockOwner = Owner.builder()
                .id(vehicleId)
                .ownerCode("123456789")
                .legalName("UAB")
                .ownerType(OwnerType.LEGAL)
                .name("qq")
                .surname("qq")
                .build();

        Vehicle vehicle = Vehicle.builder()
                .id(vehicleId)
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .owner(mockOwner)
                .build();

        Mockito.when(vehicleService.getVehicleById(vehicleId)).thenReturn(vehicle);

        mockMvc.perform(get("/vehicles/" + vehicleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(vehicleId.toString()))
                .andExpect(jsonPath("$.plateNo").value("ABC123"))
                .andExpect(jsonPath("$.make").value("BMW"))
                .andExpect(jsonPath("$.model").value("E34"))
                .andExpect(jsonPath("$.ownerName").value("qq"))
                .andExpect(jsonPath("$.ownerSurname").value("qq"))
                .andExpect(jsonPath("$.ownerLegalName").value("UAB"))
                .andExpect(jsonPath("$.ownerCode").value("123456789"));
    }
}