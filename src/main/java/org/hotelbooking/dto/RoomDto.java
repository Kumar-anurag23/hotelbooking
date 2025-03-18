package org.hotelbooking.dto;

import lombok.*;
import org.hotelbooking.models.Room;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private Long hotel_Id;
    private Room.RoomType roomType;
    private int person;
    private boolean available;
}