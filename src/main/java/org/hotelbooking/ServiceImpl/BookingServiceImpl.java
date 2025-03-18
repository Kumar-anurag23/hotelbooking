package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;
import org.hotelbooking.models.Room;
import org.hotelbooking.repository.BookingRepository;
import org.hotelbooking.repository.RoomRepository;
import org.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDto.getRoomId()));
        Room room1=new Room();
        room1.setAvailable(false);
        roomRepository.save(room1);
        Booking booking = objectMapper.convertValue(bookingDto, Booking.class);
        booking.setRoom(room);
        Booking savedBooking = bookingRepository.save(booking);
        return objectMapper.convertValue(savedBooking, BookingDto.class);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto, Long id){
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        Booking updatedBooking = objectMapper.convertValue(bookingDto, Booking.class);
        updatedBooking.setId(existingBooking.getId());
        updatedBooking.setRoom(existingBooking.getRoom());
        Booking savedBooking = bookingRepository.save(updatedBooking);
        return objectMapper.convertValue(savedBooking, BookingDto.class);
    }

    @Override
    public BookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        return objectMapper.convertValue(booking, BookingDto.class);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public String deleteBooking(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
            return "Booking deleted successfully";
        }
        return "Booking not found";
    }
}