package org.hotelbooking.repository;

import org.hotelbooking.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    Page<Room> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);
    @Query("SELECT COUNT(r) FROM Room r WHERE r.roomNumber = :roomNumber AND r.hotel.id = :hotelId ")
    int findRoomCount(@Param("roomNumber") String roomNumber,@Param("hotelId") Long hotelId);
}
