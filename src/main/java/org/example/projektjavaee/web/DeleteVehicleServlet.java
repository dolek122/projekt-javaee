package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.Role;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.VehicleService;

import java.io.IOException;

@WebServlet("/delete-vehicle")
public class DeleteVehicleServlet extends HttpServlet {

    @Inject
    private VehicleService vehicleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                vehicleService.deleteVehicle(id);
            } catch (NumberFormatException ignored) {
            }
        }

        resp.sendRedirect("admin");
    }
}
