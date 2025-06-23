package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.service.VehicleService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//panel admina dodawania i usuwanie pojazdow

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

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

        List<Vehicle> pojazdy = vehicleService.getAllVehicles();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2>Panel Administratora</h2>");

        // Formularz dodawania pojazdu
        out.println("<h3>Dodaj pojazd</h3>");
        out.println("<form method='post'>");
        out.println("Nazwa: <input type='text' name='name' required><br>");
        out.println("Typ: <input type='text' name='type' required><br>");
        out.println("Cena za dzień: <input type='number' step='0.01' name='price' required><br>");
        out.println("<input type='submit' value='Dodaj pojazd'>");
        out.println("</form>");

        // Lista pojazdów
        out.println("<h3>Istniejące pojazdy</h3>");
        for (Vehicle v : pojazdy) {
            out.println(v.getId() + ": " + v.getName() + " | " + v.getType() + " | " + v.getPricePerDay() + " zł ");
            out.println("<a href='delete-vehicle?id=" + v.getId() + "' onclick='return confirm(\"Na pewno usunąć?\")'>Usuń</a>");
            out.println("<br>");
        }

        out.println("<br><a href='logout'>Wyloguj</a>");
        out.println("</body></html>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String name = req.getParameter("name");
        String type = req.getParameter("type");
        double price = Double.parseDouble(req.getParameter("price"));

        Vehicle v = new Vehicle();
        v.setName(name);
        v.setType(type);
        v.setPricePerDay(price);
        v.setStatus(VehicleStatus.DOSTEPNY);

        vehicleService.addVehicle(v);

        resp.sendRedirect("admin");
    }
}
