package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.RoomDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.models.Room;
import org.hotelbooking.repository.HotelRepository;
import org.hotelbooking.repository.RoomRepository;
import org.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Hotels hotel = hotelRepository.findById(roomDto.getHotel_Id())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDto.getHotel_Id()));
        Room room = new Room();
         room.setId(roomDto.getId());
        room.setHotel(hotel);
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomType(Room.RoomType.valueOf(roomDto.getRoomType()));
        room.setCapacity(roomDto.getCapacity());
        room.setPricePerNight(roomDto.getPricePerNight());
        room.setAvailable(roomDto.isAvailable());
        Room savedRoom = roomRepository.save(room);
        return objectMapper.convertValue(savedRoom, RoomDto.class);
    }

    @Override
    public List<Room> getByHotelId(Long hotelId) {
        List<Room>l1=roomRepository.findByHotelId(hotelId);
        return l1;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return objectMapper.convertValue(room, RoomDto.class);
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDto, Long id) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        existingRoom.setAvailable(roomDto.isAvailable());
         existingRoom.setRoomNumber(roomDto.getRoomNumber());
         existingRoom.setPricePerNight(roomDto.getPricePerNight());
         existingRoom.setCapacity(roomDto.getCapacity());
        existingRoom.setRoomType(Room.RoomType.valueOf(roomDto.getRoomType()));


        if (!existingRoom.getHotel().getId().equals(roomDto.getHotel_Id())) {
            Hotels hotel = hotelRepository.findById(roomDto.getHotel_Id())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDto.getHotel_Id()));
            existingRoom.setHotel(hotel);
        }

        Room updatedRoom = roomRepository.save(existingRoom);
        return objectMapper.convertValue(updatedRoom, RoomDto.class);
    }

 @Override
    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}