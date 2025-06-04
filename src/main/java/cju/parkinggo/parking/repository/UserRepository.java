package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFcmTokenIsNotNull();
    Optional<User> findByKakaoId(String kakaoId);
}

