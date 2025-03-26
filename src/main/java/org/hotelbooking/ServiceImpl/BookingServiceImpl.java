package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.models.Room;
import org.hotelbooking.repository.BookingRepository;
import org.hotelbooking.repository.HotelRepository;
import org.hotelbooking.repository.RoomRepository;
import org.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private  EmailServiceImpl emailService;

    @Override
    @CacheEvict(value = "booking", allEntries = true)
    public BookingDto createBooking(BookingDto bookingDto) {
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDto.getRoomId()));

        Hotels hotel = hotelRepository.findById(bookingDto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + bookingDto.getHotelId()));

        boolean isRoomBooked = bookingRepository.existsByRoomAndCheckInDate(room, bookingDto.getCheckInDate());
        if (isRoomBooked) {
            throw new RuntimeException("Room is already booked for the given check-in date: " + bookingDto.getCheckInDate());
        }
        Booking booking = objectMapper.convertValue(bookingDto, Booking.class);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setRoomNumber(room.getRoomNumber());
        System.out.println("Booking object before save: " + booking);
        Booking savedBooking = bookingRepository.save(booking);
        room.setAvailable(false);
        roomRepository.save(room);
        BookingDto savedBookingDto = objectMapper.convertValue(savedBooking, BookingDto.class);
        savedBookingDto.setRoomId(savedBooking.getRoom().getId());
        savedBookingDto.setHotelId(savedBooking.getHotel().getId());
        emailService.sendConfirmationEmail(bookingDto.getEmail(),booking.getName(),bookingDto.getRoomNumber(),bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate(),bookingDto.getTotalPrice()
        );
        return savedBookingDto;
    }
    @Override
    @CachePut(value = "booking",key = "#id")
    @CacheEvict(value = "booking", allEntries = true)
    public BookingDto updateBooking(BookingDto bookingDto, Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDto.getRoomId()));

        Hotels hotel = hotelRepository.findById(bookingDto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + bookingDto.getHotelId()));

        existingBooking.setName(bookingDto.getName());
        existingBooking.setEmail(bookingDto.getEmail());
        existingBooking.setCheckInDate(bookingDto.getCheckInDate());
        existingBooking.setCheckOutDate(bookingDto.getCheckOutDate());
        existingBooking.setTotalPrice(bookingDto.getTotalPrice());
        existingBooking.setStatus(bookingDto.getStatus());
        existingBooking.setPaymentStatus(bookingDto.getPaymentStatus());
        existingBooking.setRoom(room);
        existingBooking.setRoomNumber(room.getRoomNumber());
        existingBooking.setHotel(hotel);
        Booking savedBooking = bookingRepository.save(existingBooking);
        return objectMapper.convertValue(savedBooking, BookingDto.class);
    }

    @Override
    @Cacheable(value = "booking", key = "#id != null ? #id : 'default'")
    public BookingDto getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        return objectMapper.convertValue(booking, BookingDto.class);
    }


    @Override
    @Cacheable(value = "booking",key = "{#page, #size, #sortBy, #sortDir}")
    public List<Booking> getAllBookings(int size,int page ,String sortBy,String sortDir) {
        Sort sort=Sort.by(Sort.Direction.fromString(sortBy),sortDir);
        Pageable pageable= PageRequest.of(page,size,sort);
        return bookingRepository.findAll(pageable).getContent();
    }

    @Override
    @CacheEvict(value = "booking", allEntries = true)
    public void deleteBookingByHotelIdAndRoomNumber(Long hotelId, String roomNumber) {
        Hotels hotels=hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));
       int bookings = bookingRepository.deleteByHotelAndRoom(hotelId, roomNumber);
        if (bookings<0) {
            throw new RuntimeException("Booking not found with id: " + hotelId);
        }
        System.out.println("Booking object after delete: " + bookings);
    }

    @Override
    public BookingDto cancelBooking(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        if(!"CONFIRMED".equals(booking.getStatus())){
          throw new RuntimeException("Booking status is not CONFIRMED");
        }
        LocalDate today = LocalDate.now();
        LocalDate checkInDate = booking.getCheckInDate();
        long daysBetween = ChronoUnit.DAYS.between(today, checkInDate);
        booking.setStatus("Canceled");
        if(daysBetween>7) {
            booking.setPaymentStatus("REFUNDED");
        }else if (daysBetween >= 2) {

            booking.setPaymentStatus("REFUND_PENDING");
        } else {

            booking.setPaymentStatus("CANCELLED_NO_REFUND");
        }
       emailService.sendCancellationEmail(booking.getEmail(),booking.getName(),
               booking.getRoomNumber(),
               booking.getCheckInDate(),

               booking.getCheckOutDate());
       bookingRepository.save(booking);
     return null;
    }

    @Override
    public List<Booking> getBookings(Long hotelId, String status, LocalDate checkInDate) {
        Hotels hotels=hotelRepository.findById(hotelId).orElseThrow(()->new RuntimeException("hotel id not found"));
        List<Booking> booking=bookingRepository.findStatusAndCheckInDate(hotelId,status,checkInDate);
        return booking;
    }

}
