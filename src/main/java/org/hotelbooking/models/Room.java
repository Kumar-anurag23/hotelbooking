package org.hotelbooking.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "room")
public class Room {
    @Id
    private Long id;
    @Column(name = "hotel_name", nullable = false)
    private String hotelName;
    @ManyToOne
    @JoinColumn(name = "booking_room_number")
    private Booking booking;
    @Column(name = "room_type",nullable = false)
    private  String roomType;
    @Column(name = "person",nullable = false)
    private  int person;
    @ManyToOne
    @JoinColumn(name = "hotels_id")
    private Hotels hotels;
    @Column(name ="availability", nullable = false)
    private  boolean status;
}