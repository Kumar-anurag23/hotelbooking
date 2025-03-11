package org.hotelbooking.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private Long hotelId;
    private String name;
    private String email;
    private String roomType;
    private Long roomNumber;
    private Integer capacity;
    private LocalDate checkInDate;
}
