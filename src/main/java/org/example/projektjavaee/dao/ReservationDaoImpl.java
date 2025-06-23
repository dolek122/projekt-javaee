package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.projektjavaee.model.Reservation;
import org.example.projektjavaee.model.User;

import java.util.List;

@Stateless
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByUser(User user) {
        TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.user = :user", Reservation.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    @Override
    public void delete(Long id) {
        Reservation r = em.find(Reservation.class, id);
        if (r != null) {
            em.remove(r);
        }
    }
}
