package cju.parkinggo.parking.repository;

import cju.parkinggo.parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFcmTokenIsNotNull();
}

