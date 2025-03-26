package org.hotelbooking.service;

import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);
    BookingDto updateBooking(BookingDto bookingDto,Long id);
    BookingDto getBooking(Long bookingId);
    List<Booking> getAllBookings(int size,int page ,String sortBy,String sortDir);
    String deleteBooking(Long bookingId);
    BookingDto cancelBooking(Long bookingId);
    List<Booking> getBookings(Long HotelId, String status, LocalDate checkInDate);
}
