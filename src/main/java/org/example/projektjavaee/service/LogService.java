package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.LogDao;
import org.example.projektjavaee.model.LogEntry;

import java.time.LocalDateTime;
import java.util.List;

//zapisywanie zdarzen systemowych

@Stateless
public class LogService {

    @Inject
    private LogDao logDao;

    public void log(String action) {
        LogEntry entry = new LogEntry();
        entry.setAction(action);
        entry.setTimestamp(LocalDateTime.now());
        logDao.save(entry);
    }

    public List<LogEntry> getAllLogs() {
        return logDao.findAll();
    }
}
