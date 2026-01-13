package cju.parkinggo.parking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * FCM 토큰 등록 요청용 DTO
 */
@Getter @Setter @NoArgsConstructor
public class FcmTokenRequestDto {
    private String kakaoId;
    private String fcmToken;
}
