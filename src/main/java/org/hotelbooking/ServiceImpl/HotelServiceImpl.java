package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.repository.HotelRepository;
import org.hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;


@Service
public class HotelServiceImpl implements HotelService {

 @Autowired
 private HotelRepository hotelRepository;

 @Autowired
 private ObjectMapper objectMapper;

 @Override
 @CacheEvict(value = "hotels", allEntries = true)
 public HotelDto create(HotelDto hotelDto) {
  Hotels hotelEntity = objectMapper.convertValue(hotelDto, Hotels.class);
  Hotels savedHotel = hotelRepository.save(hotelEntity);
  return objectMapper.convertValue(savedHotel, HotelDto.class);
 }

 @Override
 @Cacheable(value = "hotels", key = "{#page, #size, #sortBy, #sortDir}"
 )
 public List<Hotels> getAllHotels(int size, int page, String sortBy, String sortDir) {
  Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
  Pageable pageable = PageRequest.of(page, size, sort);
  return hotelRepository.findAll(pageable).getContent();
 }
 @Override
 @Cacheable(value = "hotels", key = "#id")
 public HotelDto getHotelById(Long id) {
  Hotels hotels = hotelRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
  return objectMapper.convertValue(hotels, HotelDto.class);
 }

 @Override
 @CachePut(value = "hotels", key = "#id")
 @CacheEvict(value = "hotels", key = "'all'")
 public HotelDto updateHotel(HotelDto hotelDto, Long id) {
  Hotels existingHotel = hotelRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
  existingHotel.setName(hotelDto.getName());
  existingHotel.setCity(hotelDto.getCity());
  existingHotel.setAddress(hotelDto.getAddress());
  existingHotel.setCountry(hotelDto.getCountry());
  existingHotel.setEmail(hotelDto.getEmail());
  existingHotel.setPhoneNumber(hotelDto.getPhoneNumber());
  existingHotel.setDescription(hotelDto.getDescription());
  existingHotel.setStatus(hotelDto.isStatus());

  Hotels savedHotel = hotelRepository.save(existingHotel);
  return objectMapper.convertValue(savedHotel, HotelDto.class);
 }

 @Override
 @CacheEvict(value = "hotels", key = "#id")
 public void deleteHotel(Long id) {
  if (hotelRepository.existsById(id)) {
   hotelRepository.deleteById(id);
  }
  hotelRepository.resetAutoIncrement();
 }
}
