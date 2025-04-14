package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.ParkingAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ParkingAvailabilityRepository extends JpaRepository<ParkingAvailability, Long> {
    Optional<ParkingAvailability> findByParking_Id(Long parkingId);
}
