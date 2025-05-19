package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.dto.ParkingStatusDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.service.ParkingService;
import cju.parkinggo.parking.service.ParkingStatusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private ParkingRepository parkingRepository;

    // 수동 생성자 주입
    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/{id}")
    public String getParking(@PathVariable Long id) {
        return parkingService.getParking(id).toString();
    }

    @PostMapping
    public ResponseEntity<ParkingDto> createParking(@RequestBody ParkingDto dto) {
        return ResponseEntity.ok(parkingService.createParking(dto));
    }

    @GetMapping
    public List<ParkingDto> getAllParking() {
        return parkingService.getAllParking();
    }

    @Operation(summary = "주차장 삭제", description = "주차장과 해당 주차장의 빈자리 정보를 함께 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
        return ResponseEntity.noContent().build();
    }

    @RestController
    @RequestMapping("/api/parking/status")
    public class ParkingStatusController {

        private final ParkingStatusService parkingStatusService;

        @Autowired
        public ParkingStatusController(ParkingStatusService parkingStatusService) {
            this.parkingStatusService = parkingStatusService;
        }

        @PostMapping
        public void receiveStatusFromAI(@RequestBody List<ParkingStatusDto> statusList) {
            parkingStatusService.updateStatus(statusList);
        }
    }
    @PutMapping("/name/{parkingName}")
    public ResponseEntity<?> updateByParkingName(
            @PathVariable String parkingName,
            @RequestBody Map<String, String> body) {

        // 주차장 검색
        Parking parking = parkingRepository.findByParkingName(parkingName)
                .orElseThrow(() -> new RuntimeException("해당 주차장이 존재하지 않습니다: " + parkingName));

        // 수정할 값 설정
        parking.setName(body.get("name"));
        parking.setLocation(body.get("location"));

        // 저장
        parkingRepository.save(parking);

        return ResponseEntity.ok().body("수정 완료: " + parkingName);
    }

}
