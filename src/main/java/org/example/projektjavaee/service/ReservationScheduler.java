package org.example.projektjavaee.service;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.ReservationDao;
import org.example.projektjavaee.model.Reservation;
import org.example.projektjavaee.model.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

@Singleton
@Startup
public class ReservationScheduler {

    @Inject
    private ReservationDao reservationDao;

    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)
    public void cancelUnpaidReservations() {
        List<Reservation> all = reservationDao.findAll();

        for (Reservation r : all) {
            if (r.getStatus() == ReservationStatus.ZAREZERWOWANA &&
                    !r.isPaymentConfirmed() &&
                    r.getStartDate().isBefore(LocalDate.now().minusDays(3))) {

                r.setStatus(ReservationStatus.ANULOWANA);
                reservationDao.update(r);
                System.out.println("[SCHEDULER] Rezerwacja ID " + r.getId() + " została automatycznie anulowana.");
            }
        }
    }
}
