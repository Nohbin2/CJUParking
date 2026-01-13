package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 🔥 이 메서드가 반드시 있어야 합니다!
    Optional<User> findByKakaoId(String kakaoId);
}
