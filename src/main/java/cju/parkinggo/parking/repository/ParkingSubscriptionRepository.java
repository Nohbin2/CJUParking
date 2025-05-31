package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.ParkingSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSubscriptionRepository extends JpaRepository<ParkingSubscription, Long> {
    List<ParkingSubscription> findByParkingId(Long parkingId);
    boolean existsByUserIdAndParkingId(Long userId, Long parkingId);
    void deleteByUserIdAndParkingId(Long userId, Long parkingId);
}
