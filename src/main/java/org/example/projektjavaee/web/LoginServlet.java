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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.login(email, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            if (user.getRole() == Role.ADMIN) {
                resp.sendRedirect("admin");
            } else {
                resp.sendRedirect("rezerwacje");
            }
        } else {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<h3>Błędny e-mail lub hasło.</h3>");
            out.println("<a href='login.html'>Spróbuj ponownie</a>");
            out.println("</body></html>");
        }
    }
}


