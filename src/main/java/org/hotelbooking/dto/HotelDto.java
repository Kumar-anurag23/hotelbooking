package org.hotelbooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Double longitude;

    private Double latitude;
}