package org.hotelbooking.ServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.hotelbooking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${hotel.contact.email:support@hotelbooking.com}")
    private String supportEmail;

    @Value("${hotel.website.url:https://yourhotelwebsite.com}")
    private String hotelWebsiteUrl;

    @Override
    public void sendConfirmationEmail(String to, String name, String roomNumber,
                                      LocalDate checkInDate, LocalDate checkOutDate,
                                      double totalPrice) {
        String subject = "Booking Confirmation - Your Reservation is Confirmed";
        String htmlContent = buildConfirmationEmail(name, roomNumber, checkInDate, checkOutDate, totalPrice);
        sendHtmlEmail(to, subject, htmlContent);
    }

    @Override
    public void sendCancellationEmail(String to, String name, String roomNumber,
                                      LocalDate checkInDate, LocalDate checkOutDate) {
        String subject = "Booking Cancellation - Your Reservation has been Cancelled";
        String htmlContent = buildCancellationEmail(name, roomNumber, checkInDate, checkOutDate);
        sendHtmlEmail(to, subject, htmlContent);
    }

    private String buildConfirmationEmail(String name, String roomNumber,
                                          LocalDate checkInDate, LocalDate checkOutDate,
                                          double totalPrice) {
        String formattedCheckIn = formatDate(checkInDate);
        String formattedCheckOut = formatDate(checkOutDate);
        String formattedPrice = String.format("%.2f", totalPrice);

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Booking Confirmation</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }" +
                ".content { padding: 20px; border: 1px solid #ddd; border-top: none; border-radius: 0 0 5px 5px; }" +
                ".details { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }" +
                ".footer { margin-top: 20px; font-size: 0.9em; color: #777; text-align: center; }" +
                ".button { display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin: 10px 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"header\">" +
                "<h1>Booking Confirmation</h1>" +
                "</div>" +
                "<div class=\"content\">" +
                "<p>Dear " + name + ",</p>" +
                "<p>Your booking has been confirmed with the following details:</p>" +
                "<div class=\"details\">" +
                "<p><strong>Room Number:</strong> " + roomNumber + "</p>" +
                "<p><strong>Check-in Date:</strong> " + formattedCheckIn + "</p>" +
                "<p><strong>Check-out Date:</strong> " + formattedCheckOut + "</p>" +
                "<p><strong>Total Price:</strong> $" + formattedPrice + "</p>" +
                "</div>" +
                "<p>Thank you for choosing our hotel!</p>" +
                "<p>If you have any questions, please contact our support team.</p>" +
                "<div style=\"text-align: center;\">" +
                "<a href=\""  + "/contact\" class=\"button\">Contact Support</a>" +
                "</div>" +
                "</div>" +
                "<div class=\"footer\">" +
                "<p>© 2023 Hotel Booking System. All rights reserved.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    private String buildCancellationEmail(String name, String roomNumber,
                                          LocalDate checkInDate, LocalDate checkOutDate) {
        String formattedCheckIn = formatDate(checkInDate);
        String formattedCheckOut = formatDate(checkOutDate);

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Booking Cancellation</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #f44336; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }" +
                ".content { padding: 20px; border: 5px solid black; border-top: none; border-radius: 0 0 5px 5px; }"+
                ".details { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }" +
                ".footer { margin-top: 20px; font-size: 0.9em; color: #777; text-align: center; }" +
                ".button { display: inline-block; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; margin: 10px 5px; }" +
                ".button-contact { background-color: #f44336; }" +
                ".button-rebook { background-color: #4CAF50; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"header\">" +
                "<h1>Booking Cancellation</h1>" +
                "</div>" +
                "<div class=\"content\">" +
                "<p>Dear " + name + ",</p>" +
                "<p>Your booking has been cancelled with the following details:</p>" +
                "<div class=\"details\">" +
                "<p><strong>Room Number:</strong> " + roomNumber + "</p>" +
                "<p><strong>Check-in Date:</strong> " + formattedCheckIn + "</p>" +
                "<p><strong>Check-out Date:</strong> " + formattedCheckOut + "</p>" +
                "</div>" +
                "<p>If this was a mistake or you need assistance, please contact our support team.</p>" +
                "<p>We hope to serve you again in the future.</p>" +
                "<div style=\"text-align: center;\">" +
                "<a href=\"" + hotelWebsiteUrl + "/contact\" class=\"button button-contact\">Contact Support</a>" +
                "<a href=\"" + hotelWebsiteUrl + "/rebook\" class=\"button button-rebook\">Book Again</a>" +
                "</div>" +
                "</div>" +
                "<div class=\"footer\">" +
                "<p>© 2023 Hotel Booking System. All rights reserved.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(mimeMessage);
            System.out.println("HTML email sent successfully to: " + to);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to: " + to);
            e.printStackTrace();
        }
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
    }
}