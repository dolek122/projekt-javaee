package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Role;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserService;

import java.io.IOException;

@WebServlet("/register")
public class UserServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleParam = req.getParameter("role");

        Role role = Role.valueOf(roleParam.toUpperCase());

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // w przyszłości: zahaszować!
        user.setRole(role);

        try {
            userService.registerUser(user);
            resp.sendRedirect("register-success.html");
        } catch (RuntimeException e) {
            resp.sendRedirect("register-failure.html");
        }
    }
}
