package org.hotelbooking.repository;

import jakarta.transaction.Transactional;
import org.hotelbooking.models.Booking;
import org.hotelbooking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room AND b.checkInDate = :checkInDate")
    boolean existsByRoomAndCheckInDate(
            @Param("room") Room room,
            @Param("checkInDate") LocalDate checkInDate
    );
    @Query("select count(b) from Booking  b where b.room.id = :roomID")
    int countByRoomId(@Param("roomId") Long roomId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM booking WHERE hotel_id = ?1 AND room_number = ?2", nativeQuery = true)
    int deleteByHotelAndRoom(@Param("hotelId") Long hotelId, @Param("roomNumber") String roomNumber);
    @Query("SELECT b FROM Booking b WHERE b.hotel.id = :hotelId " +
            "AND b.status = :status " +
            "AND b.checkInDate = :checkInDate")
    List<Booking> findStatusAndCheckInDate(@Param("hotelId")long hotelId,@Param("status")String status,@Param("checkInDate")LocalDate checkInDate);
}
