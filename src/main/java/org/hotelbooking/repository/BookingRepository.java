package org.hotelbooking.repository;

import org.hotelbooking.models.Booking;
import org.hotelbooking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room AND b.checkInDate = :checkInDate")
    boolean existsByRoomAndCheckInDate(
            @Param("room") Room room,
            @Param("checkInDate") LocalDate checkInDate
    );
}
