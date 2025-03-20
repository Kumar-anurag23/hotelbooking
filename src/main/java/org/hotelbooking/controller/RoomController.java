package org.hotelbooking.controller;

import org.hotelbooking.Reponse.ResponseHandler;
import org.hotelbooking.dto.HotelDto;
import org.hotelbooking.dto.RoomDto;
import org.hotelbooking.models.Room;
import org.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<Object> createRoom(@RequestBody RoomDto roomDto) {
        RoomDto newRoomDto = roomService.createRoom(roomDto);
        if (newRoomDto != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "room register", true, newRoomDto);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "room register", false, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateRoom(@RequestBody RoomDto roomDto, @PathVariable Long id) {
        RoomDto roomDto1 = roomService.updateRoom(roomDto, id);
        if (roomDto1 != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "room update", true, roomDto1);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "room update", false, null);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        boolean isDeleted = roomService.deleteRoom(id);
        if (isDeleted) {
            return ResponseHandler.getResponse(HttpStatus.OK, "Room deleted successfully", true, id);
        } else {
            return ResponseHandler.getResponse(HttpStatus.NOT_FOUND, "Room not found with id: " + id, false, null);
        }
    }

    @GetMapping("getid/{id}")
    public ResponseEntity<Object> getRoom(@PathVariable Long id) {
        RoomDto roomDto = roomService.getRoomById(id);
        if (roomDto != null) {
            return ResponseHandler.getResponse(HttpStatus.OK, "room get", true, roomDto);
        }
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "room get", false, null);
    }

    @GetMapping("/roombyhid/{hotelId}")
    public ResponseEntity<Object> getHotelWithRooms(@PathVariable Long hotelId) {
        try {
            HotelDto hotelDto = roomService.getByHotelId(hotelId);
            return ResponseHandler.getResponse(HttpStatus.OK, "Hotel details fetched successfully", false, hotelDto);
        } catch (Exception e) {
            return ResponseHandler.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch hotel details", true, null);
        }
    }

    }
