package org.hotelbooking.service;

import org.hotelbooking.dto.RoomDto;
import org.hotelbooking.models.Room;

import java.util.List;

public interface RoomService {

    RoomDto createRoom(RoomDto roomDto);
    List<Room> getAllRooms();
    RoomDto getRoomById(Long id);
    RoomDto updateRoom(RoomDto roomDto,Long id);
    void deleteRoom(Long id);

}
