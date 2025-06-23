package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.service.ReservationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//zarzadzanie rezerwacjami

@WebServlet("/admin-rezerwacje")
public class AdminReservationsServlet extends HttpServlet {

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<Reservation> reservations = reservationService.getAllReservations();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2>Wszystkie rezerwacje</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Użytkownik</th><th>Pojazd</th><th>Od</th><th>Do</th><th>Status</th><th>Akcja</th></tr>");

        for (Reservation r : reservations) {
            out.println("<tr>");
            out.println("<td>" + r.getId() + "</td>");
            out.println("<td>" + r.getUser().getEmail() + "</td>");
            out.println("<td>" + r.getVehicle().getName() + "</td>");
            out.println("<td>" + r.getStartDate() + "</td>");
            out.println("<td>" + r.getEndDate() + "</td>");
            out.println("<td>" + r.getStatus() + "</td>");

            // Dodaj przycisk anulowania
            if (r.getStatus() != ReservationStatus.ANULOWANA) {
                out.println("<td><a href='anuluj-rezerwacje?id=" + r.getId() + "' onclick='return confirm(\"Na pewno anulować?\")'>Anuluj</a></td>");
            } else {
                out.println("<td>-</td>");
            }

            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br><a href='admin'>Powrót do panelu admina</a>");
        out.println("<br><a href='logout'>Wyloguj</a>");
        out.println("</body></html>");
    }
}
