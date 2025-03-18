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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Add this line
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

   @Column(name = "guest")
    private  Integer guest;
}
