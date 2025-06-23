package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.UserDao;
import org.example.projektjavaee.model.User;

import java.util.List;

//rejestracja i logowanie

@Stateless
public class UserService {

    @Inject
    private UserDao userDao;

    public void registerUser(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Użytkownik z tym adresem e-mail już istnieje.");
        }
        userDao.create(user);
    }

    public User login(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return null; // lub można rzucać wyjątek
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
