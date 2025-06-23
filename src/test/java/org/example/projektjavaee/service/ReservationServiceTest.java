package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.ReservationDao;
import org.example.projektjavaee.model.*;
import org.example.projektjavaee.utils.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationDao reservationDao;
    private EmailService emailService;
    private LogService logService;

    private User mockUser;
    private Vehicle mockVehicle;

    @BeforeEach
    public void setup() {
        reservationService = new ReservationService();

        reservationDao = mock(ReservationDao.class);
        emailService = mock(EmailService.class);
        logService = mock(LogService.class);

        reservationService.emailService = emailService;
        reservationService.reservationDao = reservationDao;
        reservationService.logService = logService;

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setUsername("testuser");

        mockVehicle = new Vehicle();
        mockVehicle.setName("Test Car");
        mockVehicle.setType("Samochód");
        mockVehicle.setPricePerDay(100.0);
        mockVehicle.setStatus(VehicleStatus.DOSTEPNY);
    }

    @Test
    public void testMakeReservationShouldPersistAndLog() {
        reservationService.makeReservation(mockUser, mockVehicle,
                LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 20));

        verify(reservationDao).create(any(Reservation.class));
        verify(emailService).sendEmail(eq("test@example.com"), contains("Potwierdzenie"), contains("Zarezerwowano"));
        verify(logService).log(contains("rezerwację pojazdu"));
    }

    @Test
    public void testConfirmPaymentShouldUpdateReservation() {
        Reservation res = new Reservation();
        res.setId(1L);
        res.setPaymentConfirmed(false);
        res.setStatus(ReservationStatus.ZAREZERWOWANA);
        res.setUser(mockUser);

        when(reservationDao.findById(1L)).thenReturn(res);

        reservationService.confirmPayment(1L);

        verify(reservationDao).update(res);
        verify(emailService).sendEmail(eq("test@example.com"), contains("Płatność"), contains("opłacona"));
    }

    @Test
    public void testCancelReservationShouldSetStatusToAnulowana() {
        Reservation res = new Reservation();
        res.setId(1L);
        res.setStatus(ReservationStatus.ZAREZERWOWANA);
        res.setUser(mockUser);

        when(reservationDao.findById(1L)).thenReturn(res);

        reservationService.cancelReservation(1L);

        verify(reservationDao).update(res);
        verify(emailService).sendEmail(eq("test@example.com"), contains("anulowana"), anyString());
    }
}
