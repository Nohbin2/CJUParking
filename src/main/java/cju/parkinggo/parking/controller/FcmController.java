package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingFcmSendDto;
import cju.parkinggo.parking.service.FcmService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final FcmService fcmService;

    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/send")
    public String sendParkingSpaceNotification(@RequestBody ParkingFcmSendDto dto) {
        try {
            String notificationTitle = "주차장 빈자리 알림";
            String notificationBody = String.format("%s에 빈자리가 생겼습니다!", dto.getParkingLotName());

            fcmService.sendNotification(dto.getToken(), notificationTitle, notificationBody);
            return "알림 전송 완료: " + notificationBody;
        } catch (Exception e) {
            e.printStackTrace();
            return "알림 전송 실패: " + e.getMessage();
        }
    }

}