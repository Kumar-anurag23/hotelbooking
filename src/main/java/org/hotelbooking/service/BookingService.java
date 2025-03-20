package org.hotelbooking.service;

import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);
    BookingDto updateBooking(BookingDto bookingDto,Long id);
    BookingDto getBooking(Long bookingId);
    List<Booking> getAllBookings();
    String deleteBooking(Long bookingId);

}
