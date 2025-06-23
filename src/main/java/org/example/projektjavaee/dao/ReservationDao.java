package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.Reservation;
import org.example.projektjavaee.model.User;

import java.util.List;

public interface ReservationDao {
    void create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByUser(User user);
    void update(Reservation reservation);
    void delete(Long id);
}
