package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.repository.HotelRepository;
import org.hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class HotelServiceImpl implements HotelService {

 @Autowired
 private HotelRepository hotelRepository;
 @Autowired
 private ObjectMapper objectMapper;

 @Override
 public HotelDto create(HotelDto hotelDto) {
  Hotels hotelEntity = objectMapper.convertValue(hotelDto, Hotels.class);
  Hotels savedHotel = hotelRepository.save(hotelEntity);
  HotelDto hoteldto= objectMapper.convertValue(savedHotel, HotelDto.class);
  return hoteldto;
 }

 @Override
 public List<Hotels> getAllHotels() {
  return hotelRepository.findAll();
 }

 @Override
 public HotelDto getHotelById(Long id) {
  Hotels hotels = hotelRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
  return objectMapper.convertValue(hotels, HotelDto.class);
 }


 @Override
 public HotelDto updateHotel(HotelDto hotelDto, Long id) {
  Hotels existingHotel = hotelRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

  existingHotel.setName(hotelDto.getName());
  existingHotel.setType(hotelDto.getType());
  existingHotel.setDescription(hotelDto.getDescription());
  existingHotel.setAvailableFrom(hotelDto.getAvailableFrom());
  existingHotel.setAvailableTo(hotelDto.getAvailableTo());
  existingHotel.setStatus(hotelDto.isStatus());

  Hotels savedHotel = hotelRepository.save(existingHotel);
  return objectMapper.convertValue(savedHotel, HotelDto.class);
 }



 @Override
 public void deleteHotel(Long id) {
  hotelRepository.deleteById(id);
 }
}
