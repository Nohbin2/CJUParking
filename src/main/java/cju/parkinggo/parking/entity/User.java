package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자 정보 및 FCM 토큰을 저장하는 엔티티
 */
@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor
public class User {
    public User(String kakaoId, String username, String profileImage) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.profileImage = profileImage;
    }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId; // 카카오 고유 식별자

    private String username; // 사용자 이름

    @Column(name = "profile_image")
    private String profileImage; // 카카오 프로필 이미지 URL

    @Column(name = "fcm_token")
    private String fcmToken; // FCM 알림 토큰
}
