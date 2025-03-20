package org.hotelbooking.repository;

import jakarta.transaction.Transactional;
import org.hotelbooking.models.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotels,Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE Hotels AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
