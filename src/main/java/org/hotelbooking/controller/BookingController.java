package org.hotelbooking.controller;
import org.hotelbooking.Reponse.ResponseHandler;
import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;
import org.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Object> creatBooking(@RequestBody BookingDto bookingDto) {
        BookingDto result = bookingService.createBooking(bookingDto);
        if (result != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "create booking", true, result);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBooking(@RequestBody BookingDto bookingDto, @PathVariable Long id) {
        BookingDto result = bookingService.updateBooking(bookingDto, id);
        if (result != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "update booking", true, result);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, null);
    }

    @GetMapping("/getall")
    public ResponseEntity<Object> getAllBooking() {
        List<Booking> list = bookingService.getAllBookings();
        if (list != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "get all booking", true, list);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, null);
    }

    @GetMapping("/bookingId/{id}")
    public ResponseEntity<Object> getBookingById(@PathVariable Long id) {
        BookingDto bookingDto = bookingService.getBooking(id);
        if (bookingDto != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "get booking", true, bookingDto);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, null);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Object> deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseHandler.getResponse(HttpStatus.OK, "delete booking", true, id);
    }
}