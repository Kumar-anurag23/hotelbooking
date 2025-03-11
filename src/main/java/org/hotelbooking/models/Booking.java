
package org.hotelbooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Booking")
public class Booking {


    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotels hotel;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(nullable = false,name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Id
    @Column(name = "roomNumber", nullable = false)
    private Long roomNumber;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "checkInDate",nullable = false)
    private LocalDate checkInDate;

    public enum RoomType {
        SINGLE, DOUBLE, SUITE, DELUXE
    }

}
