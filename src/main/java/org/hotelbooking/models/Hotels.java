package org.hotelbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel name cannot be blank")
    @Size(max = 125, message = "Hotel name must be less than 125 characters")
    @Column(name = "name", nullable = false, length = 125)
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must be less than 255 characters")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City must be less than 100 characters")
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 100, message = "Country must be less than 100 characters")
    @Column(name = "country", nullable = false)
    private String country;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$",
            message = "Invalid phone number format")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private boolean status;

    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    @Column(name = "longitude")
    private Double longitude;

    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> rooms;
}