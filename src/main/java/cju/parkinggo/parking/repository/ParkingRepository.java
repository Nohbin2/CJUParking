package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 주차장 정보 JPA 리포지토리
 */
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    // 주차장 이름으로 주차장 조회 (알림 대상 찾기 등)
    Optional<Parking> findByParkingName(String parkingName);
}
