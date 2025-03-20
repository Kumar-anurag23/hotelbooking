package org.hotelbooking.repository;

import jakarta.transaction.Transactional;
import org.hotelbooking.models.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotels,Long> {

    @Query("SELECT h FROM Hotels h LEFT JOIN FETCH h.rooms WHERE h.id = :hotelId")
    Optional<Hotels> findByIdWithRooms(@Param("hotelId") int hotelId);
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE Hotels AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
