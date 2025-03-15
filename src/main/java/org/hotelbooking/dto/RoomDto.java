package org.hotelbooking.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private Long hotelId;
    private String roomType;
    private int person;
    private boolean availability;
    private Long bookingId;
}