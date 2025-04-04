package org.hotelbooking.controller;

import org.hotelbooking.Reponse.ResponseHandler;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.service.HotelService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createHotel(@RequestBody HotelDto hotelDto) {
        HotelDto hotelDto1 = hotelService.create(hotelDto);
        if(hotelDto1 != null) {
           ResponseEntity<Object> responseEntity= ResponseHandler.getResponse(HttpStatus.CREATED, "user register", true,hotelDto1);
          return responseEntity;
        }
        ResponseEntity<Object> responseEntity= ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "some thing went wrong", true,hotelDto1);
        return responseEntity;
    }
    @GetMapping("find/{city}")
    public ResponseEntity<Object> getHotel(@PathVariable String city, @RequestParam int size, @RequestParam int page) {
        List<Hotels> hotels=hotelService.getAllHotelsByCity(city,size,page);
        if (hotels !=null) {

            return ResponseHandler.getResponse(HttpStatus.OK,"Hotels  found",true,hotels);
        }

        return ResponseHandler.getResponse(HttpStatus.NOT_FOUND,"Hotel not found",false,null);
    }
    //http://localhost:8080/getall
    
    @GetMapping("/getall")
    public ResponseEntity<Object> getAllHotels(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam (defaultValue = "5") int size,
                                               @RequestParam(defaultValue = "id") String str,
                                               @RequestParam(defaultValue = "asc") String sortDir, Sort sort) {
       List<Hotels> hotels=  hotelService.getAllHotels(size,page,str,sortDir);
       if(hotels != null) {
          return ResponseHandler.getResponse(HttpStatus.OK, "success",true, hotels);
       }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "some thing went wrong", false,hotels);
    }
    //http://localhost:8080/findbyid/1
    @GetMapping("/findbyid/{id}")
    public  ResponseEntity<Object> getHotelById(@PathVariable Long id) {
      HotelDto hotelDto=hotelService.getHotelById(id);
     if(hotelDto != null) {
         return ResponseHandler.getResponse(HttpStatus.OK, "success",true, hotelDto);
     }
     return  ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "id not found",false, hotelDto);
    }
    //http://localhost:8080/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        HotelDto hotelDto1=hotelService.updateHotel(hotelDto,id);
           if(hotelDto1 != null) {
               return ResponseHandler.getResponse(HttpStatus.OK, "detail updated", true, hotelDto1);
           }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "some thing went wrong", false,hotelDto1);
    }
    //http://localhost:8080/delete/1
    @DeleteMapping("/delete/{id}")
   public  String deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "Hotel deleted";
   }


}
