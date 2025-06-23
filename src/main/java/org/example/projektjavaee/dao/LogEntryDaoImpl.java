package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.projektjavaee.model.LogEntry;

import java.util.List;

@Stateless
public class LogEntryDaoImpl implements LogDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(LogEntry entry) {
        em.persist(entry);
    }

    @Override
    public List<LogEntry> findAll() {
        return em.createQuery("SELECT l FROM LogEntry l ORDER BY l.timestamp DESC", LogEntry.class)
                .getResultList();
    }
}
