package org.hotelbooking.service;

import java.time.LocalDate;

public interface EmailService {

    void sendConfirmationEmail(String to, String name, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate, double totalPrice);
    void sendCancellationEmail(String to, String name,String roomNumber,LocalDate checkInDate,LocalDate checkOutDate);

}
