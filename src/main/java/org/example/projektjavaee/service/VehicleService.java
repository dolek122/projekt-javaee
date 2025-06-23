package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.VehicleDao;
import org.example.projektjavaee.model.Vehicle;

import java.util.List;

//dodwawanie/edytowanie/usuwanie pojazdow

@Stateless
public class VehicleService {

    @Inject
    private VehicleDao vehicleDao;

    public void addVehicle(Vehicle vehicle) {
        vehicleDao.create(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleDao.findById(id);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleDao.update(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleDao.delete(id);
    }
}
