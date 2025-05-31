package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<?> registerFcmToken(
            @RequestParam Long userId,
            @RequestParam String fcmToken
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setFcmToken(fcmToken);
        userRepository.save(user);
        return ResponseEntity.ok("FCM 토큰 등록 완료");
    }
}
