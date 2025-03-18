package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;
import org.hotelbooking.repository.BookingRepository;
import org.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = objectMapper.convertValue(bookingDto, Booking.class);
        Booking booking1 = bookingRepository.save(booking);
        return objectMapper.convertValue(booking1, BookingDto.class);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto, Long id) {
        Booking booking1 = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        if (bookingDto.getId() != booking1.getId()) {
            booking1.setId(bookingDto.getId());
        }
        if (bookingDto.getName() != booking1.getName()) {
            booking1.setName(bookingDto.getName());
        }
        if (bookingDto.getCheckInDate() != booking1.getCheckInDate()) {
            booking1.setCheckInDate(bookingDto.getCheckInDate());
        }
        bookingRepository.save(booking1);
        return objectMapper.convertValue(booking1, BookingDto.class);
    }

    @Override
    public BookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Hotel not found with id: " + bookingId));
        return objectMapper.convertValue(booking, BookingDto.class);
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings;
    }

    @Override
    public String deleteBooking(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        }
        return "Booking deleted";
    }
}