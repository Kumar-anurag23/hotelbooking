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
import java.util.Optional;

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
       boolean check=bookingRepository.existsByRoomAndCheckInDate(room, bookingDto.getCheckInDate());
       if(check){
           throw new RuntimeException("Room already exists with id: " + bookingDto.getRoomId());
       }
        Booking booking = objectMapper.convertValue(bookingDto, Booking.class);
        booking.setRoom(room);
        Booking savedBooking = bookingRepository.save(booking);
        room.setAvailable(false);
        roomRepository.save(room);
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
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if(bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            bookingRepository.deleteById(bookingId);
            Room room = booking.getRoom();

            long remainingBookings = bookingRepository.countByRoomId(room.getId());

            if (remainingBookings == 0) {
                room.setAvailable(true);
                roomRepository.save(room);
            }
        }
        return "Booking not found";
    }
}