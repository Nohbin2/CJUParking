package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.service.FcmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/fcm")
public class FcmController {

    private final FcmService fcmService;

    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/send")
    public String sendTestNotification(@RequestParam String token) {
        try {
            fcmService.sendNotification(token, "주차장 알림", "근처에 빈자리가 생겼습니다!");
            return "알림 전송 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "알림 전송 실패: " + e.getMessage();
        }
    }
}
