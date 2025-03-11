package org.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private boolean status;
    private List<Long> bookedRoomNumbers;
}
