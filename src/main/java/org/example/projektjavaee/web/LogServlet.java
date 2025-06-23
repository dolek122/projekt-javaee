package org.example.projektjavaee.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.service.LogService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/logi")
public class LogServlet extends HttpServlet {

    @Inject
    private LogService logService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<LogEntry> logs = logService.getAllLogs();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Dziennik zdarzeń</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>Czas</th><th>Akcja</th></tr>");

        for (LogEntry log : logs) {
            out.println("<tr><td>" + log.getTimestamp() + "</td><td>" + log.getAction() + "</td></tr>");
        }

        out.println("</table>");
        out.println("<br><a href='admin'>Powrót do panelu admina</a>");
        out.println("<br><a href='logout'>Wyloguj</a>");
        out.println("</body></html>");
    }
}
