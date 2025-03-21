package org.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    @NotNull(message = "Room ID must not be null")
    @JsonProperty("room_Id")
    private Long roomId;

    @NotNull(message = "Hotel ID must not be null")
    @JsonProperty("hotel_Id")
    private Long hotelId;

    private String name;

    private String email;

    @JsonProperty("roomNumber")
    private String roomNumber;

    @JsonProperty("checkInDate")
    private LocalDate checkInDate;

    @JsonProperty("checkOutDate")
    private LocalDate checkOutDate;

    @JsonProperty("total_Price")
    private Double totalPrice;

    private String status;

    private String paymentStatus;
}