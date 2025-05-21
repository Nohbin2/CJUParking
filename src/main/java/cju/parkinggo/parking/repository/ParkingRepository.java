package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByParkingName(String parkingName);
}