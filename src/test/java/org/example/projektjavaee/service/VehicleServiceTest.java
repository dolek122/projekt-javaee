package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.VehicleDao;
import org.example.projektjavaee.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVehicleShouldCallDaoCreate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Opel");

        vehicleService.addVehicle(vehicle);

        verify(vehicleDao, times(1)).create(vehicle);
    }

    @Test
    void testGetAllVehiclesShouldReturnList() {
        Vehicle v1 = new Vehicle(); v1.setName("Toyota");
        Vehicle v2 = new Vehicle(); v2.setName("Ford");

        when(vehicleDao.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getName());
    }

    @Test
    void testGetVehicleByIdShouldReturnCorrectVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Mazda");

        when(vehicleDao.findById(1L)).thenReturn(vehicle);

        Vehicle result = vehicleService.getVehicleById(1L);

        assertNotNull(result);
        assertEquals("Mazda", result.getName());
    }

    @Test
    void testUpdateVehicleShouldCallDaoUpdate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("BMW");

        vehicleService.updateVehicle(vehicle);

        verify(vehicleDao, times(1)).update(vehicle);
    }

    @Test
    void testDeleteVehicleShouldCallDaoDelete() {
        vehicleService.deleteVehicle(10L);

        verify(vehicleDao, times(1)).delete(10L);
    }
}
