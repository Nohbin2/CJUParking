package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParkingAvailabilityService {

    private final ParkingAvailabilityRepository availabilityRepository;
    private final FcmService fcmService; // ✅ FCM 서비스 주입

    public ParkingAvailabilityService(ParkingAvailabilityRepository availabilityRepository,
                                      FcmService fcmService) {
        this.availabilityRepository = availabilityRepository;
        this.fcmService = fcmService;
    }

    public ParkingAvailabilityDto getParkingAvailability(Long parkingId) {
        ParkingAvailability availability = availabilityRepository.findByParking_Id(parkingId)

                .orElseThrow(() -> new RuntimeException("해당 주차장의 빈자리가 없습니다."));
        return new ParkingAvailabilityDto(
                availability.getParking().getId(),
                availability.getEmptySpots(),
                availability.getUpdatedAt()
        );
    }

    public ParkingAvailabilityDto updateParkingAvailability(Long parkingId, int emptySpots) {
        ParkingAvailability availability = availabilityRepository.findByParking_Id(parkingId)

                .orElseThrow(() -> new RuntimeException("해당 주차장의 빈자리가 없습니다."));

        int beforeSpots = availability.getEmptySpots(); // 🔍 이전 빈자리 저장
        availability.setEmptySpots(emptySpots);
        availability.setUpdatedAt(LocalDateTime.now());
        availabilityRepository.save(availability);

        // ✅ 알림 조건: 0 → 1 이상으로 증가할 때
        if (beforeSpots == 0 && emptySpots > 0) {
            try {
                String testToken = "FCM_테스트_토큰"; // 임시 토큰
                fcmService.sendNotification(testToken, "주차장 알림", "빈자리가 생겼습니다!");
            } catch (Exception e) {
                e.printStackTrace(); // 실패 로그
            }
        }

        return new ParkingAvailabilityDto(
                availability.getParking().getId(),
                availability.getEmptySpots(),
                availability.getUpdatedAt()
        );
    }
}
