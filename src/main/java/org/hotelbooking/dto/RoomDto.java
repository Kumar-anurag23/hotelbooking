package org.hotelbooking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private Long hotel_Id;
    private String roomNumber;
    private String roomType;
    private int capacity;
    private Double pricePerNight;
    private boolean available;
}