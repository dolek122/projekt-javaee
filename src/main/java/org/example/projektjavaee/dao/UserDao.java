package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    void update(User user);
    void delete(Long id);
}
