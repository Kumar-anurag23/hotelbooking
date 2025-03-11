package org.hotelbooking.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotels hotel;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "room_number", nullable = false)
    private Long roomNumber;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    public enum RoomType {
        SINGLE, DOUBLE, SUITE, DELUXE
    }
}
