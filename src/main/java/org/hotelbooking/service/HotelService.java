package org.hotelbooking.service;

import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.models.Hotels;
import java.util.List;

public interface HotelService {
    HotelDto create(HotelDto hotelDto);
    List<Hotels> getAllHotels();
    HotelDto getHotelById(Long id);
    HotelDto updateHotel(HotelDto hotels,Long id);
    void deleteHotel(Long id);

}
