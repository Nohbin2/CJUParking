package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoId;        // 카카오 유저 고유 ID
    private String username;       // 닉네임
    private String profileImage;   // 프로필 이미지 URL
    private String fcmToken;       // FCM 토큰

    public User(String kakaoId, String username, String profileImage, String fcmToken) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.profileImage = profileImage;
        this.fcmToken = fcmToken;
    }

    public User(String kakaoId, String username, String profileImage) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.profileImage = profileImage;
    }
}
