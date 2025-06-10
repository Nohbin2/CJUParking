package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.FavoriteParking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 사용자 즐겨찾기 JPA 리포지토리
 */
public interface FavoriteParkingRepository extends JpaRepository<FavoriteParking, Long> {
    // 특정 주차장을 즐겨찾기한 모든 사용자 목록 조회
    List<FavoriteParking> findByParkingId(Long parkingId);
}
