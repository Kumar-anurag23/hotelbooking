package org.hotelbooking.controller;
import org.hotelbooking.Reponse.ResponseHandler;
import org.hotelbooking.dto.BookingDto;
import org.hotelbooking.models.Booking;
import org.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Object> creatBooking(@RequestBody BookingDto bookingDto)
            {
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
    public ResponseEntity<Object> getAllBooking(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam (defaultValue = "5") int size,
                                                 @RequestParam(defaultValue = "id") String str,
                                                 @RequestParam(defaultValue = "asc") String sortDir) {
        List<Booking> list = bookingService.getAllBookings(size,page,str,sortDir);
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
    @PostMapping("{id}/cancel")
    public ResponseEntity<Object> cancelBooking(@PathVariable Long id) {
        BookingDto bookingDto = bookingService.cancelBooking(id);
        if (bookingDto != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "cancel booking", true, bookingDto);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, null);
    }
    @GetMapping("hotel/{hotelId}/status/{status}/date/{checkInDate}")
    public ResponseEntity<Object> getBookingStatus(@PathVariable Long hotelId, @PathVariable String status,@PathVariable LocalDate checkInDate) {
      List<Booking> list=bookingService.getBookings(hotelId,status,checkInDate);
      if (list != null) {
          return ResponseHandler.getResponse(HttpStatus.OK, "get booking status", true, list);
      }
      return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "something went wrong", false, null);
    }


    @DeleteMapping("/by-hotel-and-room")
    public ResponseEntity<Object> deleteBookingByHotelAndRoom(
            @RequestParam Long hotelId,
            @RequestParam String roomNumber) {

        bookingService.deleteBookingByHotelIdAndRoomNumber(hotelId, roomNumber);
               return ResponseHandler.getResponse(HttpStatus.OK,"delete hotel",true,null);
    }
}