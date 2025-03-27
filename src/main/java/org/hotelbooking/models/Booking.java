package org.hotelbooking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Room must be specified")
    private Room room;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Guest name cannot be blank")
    @Size(max = 100, message = "Guest name must be less than 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "Guest name can only contain letters, spaces, hyphens, and apostrophes")
    private String name;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Hotel must be specified")
    private Hotels hotel;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "room_number", nullable = false)
    @NotBlank(message = "Room number cannot be blank")
    @Size(max = 10, message = "Room number must be less than 10 characters")
    private String roomNumber;

    @Column(name = "check_in_date", nullable = false)
    @NotNull(message = "Check-in date must be specified")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    @NotNull(message = "Check-out date must be specified")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @JsonProperty("total_Price")
    @Column(name = "total_price", nullable = false)
    @NotNull(message = "Total price must be specified")
    @DecimalMin(value = "0.01", message = "Total price must be at least 0.01")
    @Digits(integer = 10, fraction = 2, message = "Total price must have up to 10 integer and 2 fraction digits")
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    @NotBlank(message = "Booking status cannot be blank")
    @Pattern(regexp = "^(CONFIRMED|PENDING|CANCELLED|COMPLETED)$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Status must be CONFIRMED, PENDING, CANCELLED, or COMPLETED")
    private String status;

    @Column(name = "payment_status", nullable = false)
    @NotBlank(message = "Payment status cannot be blank")
    @Pattern(regexp = "^(PAID|PENDING|FAILED|REFUNDED)$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Payment status must be PAID, PENDING, FAILED, or REFUNDED")
    private String paymentStatus;

    // Custom validation for date consistency
    @AssertTrue(message = "Check-out date must be after check-in date")
    private boolean isCheckOutAfterCheckIn() {
        if (checkInDate == null || checkOutDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return checkOutDate.isAfter(checkInDate);
    }
}