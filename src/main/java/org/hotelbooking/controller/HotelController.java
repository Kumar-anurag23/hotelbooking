package org.hotelbooking.controller;

import lombok.RequiredArgsConstructor;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    //http://localhost:8080/cre
    @PostMapping("/cre")
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto hotelDto1 = hotelService.create(hotelDto);
        return ResponseEntity.ok(hotelDto1);
    }
    //http://localhost:8080/getall
    @GetMapping("/getall")
    public ResponseEntity<List<Hotels>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
    //http://localhost:8080/findbyid/1
    @GetMapping("/findbyid/{id}")
    public  ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
      HotelDto hotelDto=hotelService.getHotelById(id);
      return ResponseEntity.ok(hotelDto);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        HotelDto hotelDto1=hotelService.updateHotel(hotelDto,id);
           return new ResponseEntity<>(hotelDto1, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
   public  String deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "Hotel deleted";
   }

}
