package org.hotelbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private Long id; // Manually assigned ID (no auto-generation)

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotels hotel;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(name = "available", nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    public enum RoomType {
        SINGLE, DOUBLE, SUITE, DELUXE
    }
}