package org.example.projektjavaee.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

//zawiera encje user, vehicle, reservation, logentry, z mapowaniem do tabel bazy danych przy uzyciu jpa

@Entity
@Table(name = "logs")
public class LogEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String action;

    private LocalDateTime timestamp;

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
