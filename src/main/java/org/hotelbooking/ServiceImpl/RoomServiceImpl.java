package org.hotelbooking.ServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.dto.RoomDto;
import org.hotelbooking.models.Hotels;
import org.hotelbooking.models.Room;
import org.hotelbooking.repository.HotelRepository;
import org.hotelbooking.repository.RoomRepository;
import org.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    @CacheEvict(value = "rooms" , allEntries = true)
    public RoomDto createRoom(RoomDto roomDto) {
        Hotels hotel = hotelRepository.findById(roomDto.getHotel_Id())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDto.getHotel_Id()));
        int count1=roomRepository.findRoomCount(roomDto.getRoomNumber(),roomDto.getHotel_Id());
        if(count1>0){
            throw new RuntimeException("room already exist : "+roomDto.getRoomNumber()+" in this hotel : "+roomDto.getHotel_Id());
        }

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomType(Room.RoomType.valueOf(roomDto.getRoomType()));
        room.setCapacity(roomDto.getCapacity());
        room.setPricePerNight(roomDto.getPricePerNight());
        room.setAvailable(roomDto.isAvailable());

        Room savedRoom = roomRepository.save(room);

        RoomDto saveRoomDto= objectMapper.convertValue(savedRoom, RoomDto.class);
        saveRoomDto.setHotel_Id(savedRoom.getHotel().getId());
        return saveRoomDto;
    }

    @Override
    @Cacheable(value = "hotels", key = "{#hotelId, #size, #page, #sortBy, #order}")
    public HotelDto getByHotelId(Long hotelId, int size, int page, String sortBy, String order) {
        Hotels hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));
        HotelDto hotelDto = objectMapper.convertValue(hotel, HotelDto.class);
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Room> roomsPage = roomRepository.findByHotelId(hotelId, pageable);
        List<RoomDto> roomDtos = roomsPage.getContent().stream()
                .map(room -> {
                    RoomDto roomDto = objectMapper.convertValue(room, RoomDto.class);
                    roomDto.setHotel_Id(room.getHotel().getId());
                    return roomDto;
                })
                .collect(Collectors.toList());

        hotelDto.setRooms(roomDtos);

        return hotelDto;
    }
    @Override
    @Cacheable(value = "rooms", key = "#id != null ? #id : 'default'")
    public RoomDto getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        return objectMapper.convertValue(room, RoomDto.class);
    }

    @Override
    @CachePut(value = "rooms",key = "#id")
    @CacheEvict(value = "rooms", allEntries = true)
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
    @CacheEvict(value = "room",key = "#id")
    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}