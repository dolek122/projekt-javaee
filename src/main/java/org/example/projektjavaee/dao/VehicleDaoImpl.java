package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.projektjavaee.model.Vehicle;

import java.util.List;

@Stateless
public class VehicleDaoImpl implements VehicleDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public void create(Vehicle vehicle) {
        em.persist(vehicle);
    }

    @Override
    public Vehicle findById(Long id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> findAll() {
        return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    @Override
    public void update(Vehicle vehicle) {
        em.merge(vehicle);
    }

    @Override
    public void delete(Long id) {
        Vehicle v = em.find(Vehicle.class, id);
        if (v != null) {
            em.remove(v);
        }
    }
}
