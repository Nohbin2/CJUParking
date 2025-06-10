package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자 관련 JPA 리포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // 🔥 카카오ID로 사용자 단일 조회 (FCM 토큰 저장, 즐겨찾기 등에 활용)
    Optional<User> findByKakaoId(String kakaoId);
}
