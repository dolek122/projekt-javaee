package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.LogEntry;

import java.util.List;

//udostepnia interfejsy i implementacje operujace na bazie danych

public interface LogDao {
    void save(LogEntry entry);
    List<LogEntry> findAll();
}
