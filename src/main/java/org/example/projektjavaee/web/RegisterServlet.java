package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Role;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleParam = req.getParameter("role");

        // Walidacja danych wejściowych
        if (username == null || email == null || password == null || roleParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wszystkie pola są wymagane.");
            return;
        }

        Role role;
        try {
            role = Role.valueOf(roleParam); // Konwersja z tekstu na enum
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nieprawidłowa rola.");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        try {
            userService.registerUser(user);
            resp.sendRedirect("register-success.html");
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Użytkownik już istnieje.");
        }
    }

    // Zablokuj dostęp GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Rejestracja</title></head><body>");
        out.println("<h1>Rejestracja</h1>");
        out.println("<form method='post' action='register'>");
        out.println("Nazwa użytkownika: <input type='text' name='username' required><br>");
        out.println("Email: <input type='email' name='email' required><br>");
        out.println("Hasło: <input type='password' name='password' required><br>");
        out.println("Rola: <select name='role'>");
        out.println("<option value='CLIENT'>Użytkownik</option>");
        out.println("<option value='ADMIN'>Administrator</option>");
        out.println("</select><br><br>");
        out.println("<input type='submit' value='Zarejestruj się'>");
        out.println("</form>");
        out.println("</body></html>");
    }

}
