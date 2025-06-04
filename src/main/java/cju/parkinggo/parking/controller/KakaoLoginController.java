package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.UserRepository;
import cju.parkinggo.parking.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> tokenMap) {
        String accessToken = tokenMap.get("accessToken");

        // 1. 카카오 API로 사용자 정보 요청
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, request, Map.class);
        Map<String, Object> body = response.getBody();

        String kakaoId = String.valueOf(body.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // 2. 사용자 정보 저장 or 조회
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userRepository.save(new User(kakaoId, nickname, profileImage)));

        // 3. JWT 발급
        String jwt = jwtProvider.createToken(user.getId());

        // 4. 결과 반환
        Map<String, Object> result = new HashMap<>();
        result.put("jwt", jwt);
        result.put("userId", user.getId());
        result.put("nickname", user.getUsername());
        result.put("profileImage", user.getProfileImage());



        return ResponseEntity.ok(result);
    }
}
