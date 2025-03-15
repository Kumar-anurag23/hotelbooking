package org.hotelbooking.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto{
    private Long id;
    private String name;
    private String type;
    private String description;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private boolean status;
    private List<RoomDto> rooms;
}