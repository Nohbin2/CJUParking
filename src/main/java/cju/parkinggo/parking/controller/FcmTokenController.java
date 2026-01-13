package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.FcmTokenRequestDto;
import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 사용자 FCM 토큰 등록/갱신용 API 컨트롤러
 */
@RestController
@RequestMapping("/api/fcm")
public class FcmTokenController {

    private final UserRepository userRepository;

    public FcmTokenController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * [POST] /api/fcm/token
     * 사용자의 FCM 토큰을 등록/갱신한다.
     * @param dto kakaoId, fcmToken 포함한 JSON
     */
    @PostMapping("/token")
    public String registerOrUpdateFcmToken(@RequestBody FcmTokenRequestDto dto) {
        Optional<User> userOpt = userRepository.findByKakaoId(dto.getKakaoId());
        if (userOpt.isEmpty()) {
            return "해당 카카오ID의 사용자가 존재하지 않습니다: " + dto.getKakaoId();
        }
        User user = userOpt.get();
        user.setFcmToken(dto.getFcmToken());
        userRepository.save(user);
        return "FCM 토큰이 정상적으로 저장/갱신되었습니다!";
    }
}
