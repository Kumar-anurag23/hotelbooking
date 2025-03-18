package org.hotelbooking.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private String name;
    private String email;
    private Long roomId;
    private LocalDate checkInDate;
    private Integer guest;
}