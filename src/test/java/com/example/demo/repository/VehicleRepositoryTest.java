package com.example.demo.repository;

import com.example.demo.entity.Vehicle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    public void VehicleRepository_Save_ReturnSavedVehicle() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        Assertions.assertThat(savedVehicle).isNotNull();
    }

    @Test
    public void VehicleRepository_GetAll_ReturnMoreThanOneVehicle() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();

        Vehicle vehicle2 = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC999")
                .isActive(true)
                .build();
        vehicleRepository.save(vehicle);
        vehicleRepository.save(vehicle2);

        List<Vehicle> vehicleList = vehicleRepository.findAll();

        Assertions.assertThat(vehicleList).isNotNull();
        Assertions.assertThat(vehicleList.size()).isEqualTo(2);
    }

    @Test
    public void VehicleRepository_FindById_ReturnVehicle() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();

        vehicleRepository.save(vehicle);

        Vehicle savedVehicle = vehicleRepository.findById(vehicle.getId()).get();

        Assertions.assertThat(savedVehicle).isNotNull();
    }

    @Test
    public void VehicleRepository_ExistsByPlateNo_ReturnTrue() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();

        vehicleRepository.save(vehicle);

        boolean exist = vehicleRepository.existsByPlateNo("ABC123");

        Assertions.assertThat(exist).isTrue();
    }

    @Test
    public void VehicleRepository_ExistsByPlateNo_ReturnFalse() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();

        vehicleRepository.save(vehicle);

        boolean exist = vehicleRepository.existsByPlateNo("ABC777");

        Assertions.assertThat(exist).isFalse();
    }

    @Test
    public void VehicleRepository_UpdateVehicle_VehicleModelChange() {
        Vehicle vehicle = Vehicle.builder()
                .year(Year.of(1995))
                .make("BMW")
                .model("E34")
                .plateNo("ABC123")
                .isActive(true)
                .build();
        vehicleRepository.save(vehicle);

        Vehicle vehicleSaved = vehicleRepository.findById(vehicle.getId()).get();
        vehicleSaved.setModel("E39");
        Vehicle updatedVehicle = vehicleRepository.save(vehicleSaved);

        Assertions.assertThat(updatedVehicle.getModel()).isEqualTo("E39");
    }
}