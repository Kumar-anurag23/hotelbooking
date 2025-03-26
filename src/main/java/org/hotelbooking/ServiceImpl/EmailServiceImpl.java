package org.hotelbooking.ServiceImpl;

import org.hotelbooking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {
@Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username")
    private String fromMail;
    @Override
    public void sendConfirmationEmail(String to, String name, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate, double totalPrice) {
        String subject = "Booking Confirmation - Your Reservation is Confirmed";
        String message = String.format(
                "Dear %s,\n\n" +
                        "Your booking has been confirmed with the following details:\n\n" +
                        "Room Number: %s\n" +
                        "Check-in Date: %s\n" +
                        "Check-out Date: %s\n" +
                        "Total Price: $%.2f\n\n" +
                        "Thank you for choosing our hotel!\n" +
                        "If you have any questions, please contact our support team.",
                name, roomNumber, checkInDate, checkOutDate, totalPrice
        );

        sendEmail(to, subject, message);
    }

    @Override
    public void sendCancellationEmail(String to, String name, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        String subject = "Booking Cancellation - Your Reservation has been Cancelled";
        String message = String.format(
                "Dear %s,\n\n" +
                        "Your booking has been cancelled with the following details:\n\n" +
                        "Room Number: %s\n" +
                        "Check-in Date: %s\n" +
                        "Check-out Date: %s\n" +
                        "Cancellation Reason: %s\n\n" +
                        "If this was a mistake or you need assistance, please contact our support team.\n" +
                        "We hope to serve you again in the future.",
                name, roomNumber, checkInDate, checkOutDate
        );

        sendEmail(to, subject, message);
    }
    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(fromMail);
        try {
            mailSender.send(simpleMailMessage);
            System.out.println("email sent successfully to :"+to);
        }
        catch (MailException e) {
            e.printStackTrace();
        }
    }
}
