package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;

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
    @DeleteMapping("/api/parking/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
        return ResponseEntity.noContent().build();
    }

}
