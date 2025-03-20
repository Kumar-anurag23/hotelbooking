package org.hotelbooking.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDto {

    private Long id;

    private String name;

    private String email;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Double totalPrice;

    private String paymentStatus;
}