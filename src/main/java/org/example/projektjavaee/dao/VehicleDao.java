package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    void create(Vehicle vehicle);
    Vehicle findById(Long id);
    List<Vehicle> findAll();
    void update(Vehicle vehicle);
    void delete(Long id);
}
