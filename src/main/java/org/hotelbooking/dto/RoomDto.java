package org.hotelbooking.dto;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hotelbooking.models.Booking;
import org.hotelbooking.models.Hotels;

public class RoomDto {
    @Id
    private Long id;
    private String hotelName;
    @ManyToOne
    @JoinColumn(name = "booking_room_number")
    private Booking booking;
    private  String roomType;
    private  int person;
    @ManyToOne
    @JoinColumn(name = "hotels_id")
    private Hotels hotels;
    private  boolean status;
}