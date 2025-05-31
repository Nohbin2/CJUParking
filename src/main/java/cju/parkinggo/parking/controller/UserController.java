package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<?> registerFcmToken(@RequestBody Map<String, String> body) {
        Long userId = Long.parseLong(body.get("userId"));
        String fcmToken = body.get("fcmToken");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setFcmToken(fcmToken);
        userRepository.save(user);

        return ResponseEntity.ok("FCM 토큰 등록 완료");
    }

}
