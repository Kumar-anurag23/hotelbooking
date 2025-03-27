package org.hotelbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel must be specified")
    private Hotels hotel;

    @Column(name = "room_number", nullable = false)
    @NotBlank(message = "Room number cannot be blank")
    @Size(max = 10, message = "Room number must be 10 characters or less")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Room number can only contain letters, numbers, and hyphens")
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    @NotNull(message = "Room type must be specified")
    private RoomType roomType;

    @Column(name = "capacity", nullable = false)
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 10, message = "Capacity cannot exceed 10")
    private int capacity;

    @Column(name = "price_per_night", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "Price must have up to 6 integer and 2 fraction digits")
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