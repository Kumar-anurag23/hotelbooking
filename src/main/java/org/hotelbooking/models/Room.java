package org.hotelbooking.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotels hotel;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "person", nullable = false)
    private int person;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;
}
