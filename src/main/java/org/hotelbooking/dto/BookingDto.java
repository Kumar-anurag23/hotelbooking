package org.hotelbooking.dto;

import lombok.*;
import org.hotelbooking.models.Room;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private Long hotelId;
    private String name;
    private String email;
    private Room.RoomType roomType;
    private Long roomNumber;
    private Integer capacity;
    private LocalDate checkInDate;
}