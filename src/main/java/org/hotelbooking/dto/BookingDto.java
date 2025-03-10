package org.hotelbooking.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hotelbooking.models.Booking;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    @Column(nullable = false,name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private Booking.RoomType roomType;
    @Id
    @Column(name = "roomNumber", nullable = false)
    private Long roomNumber;
    private Integer capacity;
    private LocalDate checkInDate;
}
