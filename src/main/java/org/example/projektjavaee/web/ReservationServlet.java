package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.service.ReservationService;
import org.example.projektjavaee.service.VehicleService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

//dla klientow rezerwacja i lista zarezerwowanych pojazdow

@WebServlet("/rezerwacje")
public class ReservationServlet extends HttpServlet {

    @Inject
    private VehicleService vehicleService;

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Pobierz zalogowanego użytkownika z sesji
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        List<Vehicle> pojazdy = vehicleService.getAllVehicles();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2>Witaj, " + user.getUsername() + "!</h2>");
        out.println("<h3>Dostępne pojazdy:</h3>");
        out.println("<form method='post'>");
        out.println("<select name='vehicleId'>");

        for (Vehicle v : pojazdy) {
            out.println("<option value='" + v.getId() + "'>" + v.getName() + " - " + v.getType() + " - " + v.getPricePerDay() + " zł/dzień</option>");
        }

        out.println("</select><br><br>");
        out.println("Data od: <input type='date' name='startDate' required><br>");
        out.println("Data do: <input type='date' name='endDate' required><br><br>");
        out.println("<input type='submit' value='Zarezerwuj'>");
        out.println("</form>");

        out.println("<hr>");
        out.println("<h3>Twoje rezerwacje:</h3>");
        for (Reservation r : reservationService.getUserReservations(user)) {
            out.println("Pojazd: " + r.getVehicle().getName() + " | Od: " + r.getStartDate() + " do " + r.getEndDate() + " | Status: " + r.getStatus() + "<br>");
        }

        out.println("<br><a href='logout'>Wyloguj</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        Long vehicleId = Long.parseLong(req.getParameter("vehicleId"));
        LocalDate start = LocalDate.parse(req.getParameter("startDate"));
        LocalDate end = LocalDate.parse(req.getParameter("endDate"));

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        reservationService.makeReservation(user, vehicle, start, end);

        resp.sendRedirect("rezerwacje");
    }
}
