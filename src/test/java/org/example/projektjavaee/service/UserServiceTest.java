package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.UserDao;
import org.example.projektjavaee.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserShouldCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userDao.findByEmail("test@example.com")).thenReturn(null);

        userService.registerUser(user);

        verify(userDao, times(1)).create(user);
    }

    @Test
    void testRegisterUserShouldThrowExceptionIfEmailExists() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userDao.findByEmail("existing@example.com")).thenReturn(user);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Użytkownik z tym adresem e-mail już istnieje.", ex.getMessage());
        verify(userDao, never()).create(any());
    }

    @Test
    void testLoginShouldReturnUserIfCredentialsCorrect() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("1234");

        when(userDao.findByEmail("john@example.com")).thenReturn(user);

        User result = userService.login("john@example.com", "1234");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testLoginShouldReturnNullIfUserNotFound() {
        when(userDao.findByEmail("none@example.com")).thenReturn(null);

        User result = userService.login("none@example.com", "pass");

        assertNull(result);
    }

    @Test
    void testLoginShouldReturnNullIfPasswordIncorrect() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("correct");

        when(userDao.findByEmail("john@example.com")).thenReturn(user);

        User result = userService.login("john@example.com", "wrong");

        assertNull(result);
    }

    @Test
    void testGetAllUsersShouldReturnList() {
        User u1 = new User(); u1.setEmail("a@example.com");
        User u2 = new User(); u2.setEmail("b@example.com");

        when(userDao.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void testGetUserByIdShouldReturnUser() {
        User user = new User();
        user.setEmail("id@example.com");

        when(userDao.findById(1L)).thenReturn(user);

        User result = userService.getUserById(1L);

        assertEquals("id@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserShouldCallDao() {
        User user = new User();
        user.setEmail("update@example.com");

        userService.updateUser(user);

        verify(userDao, times(1)).update(user);
    }

    @Test
    void testDeleteUserShouldCallDao() {
        userService.deleteUser(1L);
        verify(userDao, times(1)).delete(1L);
    }
}
