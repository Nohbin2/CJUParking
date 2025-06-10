package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.service.ParkingNotificationService;
import org.springframework.web.bind.annotation.*;

/**
 * (예시) 관리자/시스템에서 주차장 알림을 트리거하는 엔드포인트
 */
@RestController
@RequestMapping("/api/parking-noti")
public class ParkingNotificationController {

    private final ParkingNotificationService parkingNotificationService;

    public ParkingNotificationController(ParkingNotificationService parkingNotificationService) {
        this.parkingNotificationService = parkingNotificationService;
    }

    /**
     * 특정 주차장에 빈자리 생겼을 때 알림 발송 트리거
     * ex) POST /api/parking-noti/notify?parkingLotName=청주대학교 융합관
     */
    @PostMapping("/notify")
    public String notifyFavoriteUsers(@RequestParam String parkingLotName) {
        parkingNotificationService.notifyUsersWhenParkingAvailable(parkingLotName);
        return "알림 발송 완료";
    }
}
