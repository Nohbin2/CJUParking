package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingStatusDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/parking/status")
public class ParkingStatusController {

    private final ParkingRepository parkingRepository;
    private final ParkingAvailabilityRepository availabilityRepository;

    public ParkingStatusController(ParkingRepository parkingRepository, ParkingAvailabilityRepository availabilityRepository) {
        this.parkingRepository = parkingRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @PostMapping
    public ResponseEntity<String> receiveStatus(@RequestBody List<ParkingStatusDto> updates) {
        for (ParkingStatusDto dto : updates) {
            Parking parking = parkingRepository
                    .findByParkingName(dto.getParkingName())
                    .orElseThrow(() -> new RuntimeException("주차장 정보를 찾을 수 없습니다: " + dto.getParkingName()));


            // 총 자리 수 업데이트
            parking.setTotalSpots(dto.getTotalSpots());
            parkingRepository.save(parking);

            // ParkingAvailability 로그 기록
            ParkingAvailability availability = new ParkingAvailability();
            availability.setParking(parking);
            availability.setEmptySpots(dto.getEmptySpots());
            availability.setUpdatedAt(LocalDateTime.now());

            availabilityRepository.save(availability);
        }

        return ResponseEntity.ok("주차장 상태가 성공적으로 저장되었습니다.");
    }
}
