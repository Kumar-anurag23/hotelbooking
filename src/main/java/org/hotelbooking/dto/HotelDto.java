package org.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String email;
    private String description;
    private boolean status;
    private double longitude;
    private double latitude;
    private List<RoomDto> rooms;
}