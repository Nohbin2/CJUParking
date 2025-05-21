package cju.parkinggo.parking.repository;

import java.util.Optional;
import cju.parkinggo.parking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByParkingName(String parkingName);
}
