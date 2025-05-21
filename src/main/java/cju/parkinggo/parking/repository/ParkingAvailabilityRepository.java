package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.ParkingAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingAvailabilityRepository extends JpaRepository<ParkingAvailability, Long> {
    ParkingAvailability findTopByParkingIdOrderByUpdatedAtDesc(Long parkingId);
    List<ParkingAvailability> findByParking_Id(Long parkingId);
    @Query("""
        SELECT pa FROM ParkingAvailability pa
        WHERE pa.updatedAt IN (
        SELECT MAX(sub.updatedAt)
        FROM ParkingAvailability sub
         GROUP BY sub.parking.id
        )
    """)
    List<ParkingAvailability> findLatestAvailabilityPerParking();
}

