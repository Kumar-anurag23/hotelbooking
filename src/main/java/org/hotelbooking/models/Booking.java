//package org.hotelbooking.models;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "hotel_booking")
//public class Booking {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "name", nullable = false)
//    private String name;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Hotels hotel;
//    @Column(name = "hotelId", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private hotelType type;
//    private int hotelId;
//    @Column(name = "checkIn", nullable = false)
//    private LocalDate checkIn;
//    @Column(name = "checkOut", nullable = false)
//    private LocalDate checkOut;
//    @Column(name = "aadhar_id", nullable = false, unique = true, length = 12)
//    private String aadharId;
//    @Column( name ="status" ,nullable = false)
//    private boolean status;
//    @Column(name="guest",nullable = false)
//    private  int guest;
//}
