package cju.parkinggo.parking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ParkingFcmSendDto {
    private String kakaoId;           // 카카오 고유 ID (필수!)
    private String token;             // FCM 토큰
    private String parkingLotName;    // 주차장 이름
}