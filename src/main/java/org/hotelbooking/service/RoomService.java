package org.hotelbooking.service;

import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createRoom(RoomDto roomDto);
    HotelDto getByHotelId(Long hotelId);
    RoomDto getRoomById(Long id);
    RoomDto updateRoom(RoomDto roomDto,Long id);
    boolean deleteRoom(Long id);

}
