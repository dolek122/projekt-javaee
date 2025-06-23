package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.ReservationDao;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.utils.EmailService;

import java.time.LocalDate;
import java.util.List;

//obsluga rezerwacji i anulowania

@Stateless
public class ReservationService {

    @Inject
    LogService logService;

    @Inject
    EmailService emailService;

    @Inject
    ReservationDao reservationDao;

    public void makeReservation(User user, Vehicle vehicle, LocalDate start, LocalDate end) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setStatus(ReservationStatus.ZAREZERWOWANA);
        reservation.setPaymentConfirmed(false);

        reservationDao.create(reservation);

        // Wysyłka e-maila
        emailService.sendEmail(
                user.getEmail(),
                "Potwierdzenie rezerwacji",
                "Zarezerwowano pojazd: " + vehicle.getName() +
                        "\nOd: " + start + " do: " + end);

        logService.log("Użytkownik ID=" + user.getId() + " utworzył rezerwację pojazdu ID=" + vehicle.getId());

    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationDao.findByUser(user);
    }

    public void confirmPayment(Long reservationId) {
        Reservation r = reservationDao.findById(reservationId);
        if (r != null) {
            r.setPaymentConfirmed(true);
            r.setStatus(ReservationStatus.WYPOZYCZONA);
            reservationDao.update(r);

            // Wysyłka e-maila
            emailService.sendEmail(
                    r.getUser().getEmail(),
                    "Płatność potwierdzona",
                    "Twoja rezerwacja została opłacona i aktywowana.");
        }
    }

    public void cancelReservation(Long id) {
        Reservation r = reservationDao.findById(id);
        if (r != null) {
            r.setStatus(ReservationStatus.ANULOWANA);
            reservationDao.update(r);

            // Wysyłka e-maila
            emailService.sendEmail(
                    r.getUser().getEmail(),
                    "Rezerwacja anulowana",
                    "Twoja rezerwacja została anulowana.");
        }
    }
}
