package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.UserRepository;
import cju.parkinggo.parking.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @PostMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> codeMap) {
        String code = codeMap.get("code");
        System.out.println("🔐 받은 code: " + code);

        // 1. accessToken 요청
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", restApiKey);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, tokenHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token", tokenRequest, Map.class);

        System.out.println("카카오 코드: " + code);
        System.out.println("AccessToken 요청 응답: " + tokenResponse.getBody());

        String accessToken = (String) tokenResponse.getBody().get("access_token");
        if (accessToken == null) {
            throw new RuntimeException("카카오 access token 발급 실패");
        }

        System.out.println("access_token = " + accessToken);
        // 2. 사용자 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> userRequest = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, userRequest, Map.class);
        Map<String, Object> body = response.getBody();

        String kakaoId = String.valueOf(body.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // 3. DB 저장 또는 조회
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userRepository.save(new User(kakaoId, nickname, profileImage)));

        // 4. JWT 발급
        String jwt = jwtProvider.createToken(user.getId());

        // 5. 결과 반환
        Map<String, Object> result = new HashMap<>();
        result.put("jwt", jwt);
        result.put("userId", user.getId());
        result.put("nickname", user.getUsername());
        result.put("profileImage", user.getProfileImage());

        return ResponseEntity.ok(result);
    }
}
