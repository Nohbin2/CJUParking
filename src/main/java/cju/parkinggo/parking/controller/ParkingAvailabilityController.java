package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingAvailabilityCreateDto;
import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.service.ParkingAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingAvailabilityController {

    private final ParkingAvailabilityService availabilityService;

    public ParkingAvailabilityController(ParkingAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Operation(summary = "주차장 빈자리 정보 조회", description = "주차장의 빈자리 정보를 조회합니다.")
    @GetMapping("/api/parking/availability/{parkingId}")
    public ResponseEntity<ParkingAvailabilityDto> getAvailability(
            @Parameter(description = "조회할 주차장 ID", required = true) @PathVariable Long parkingId) {
        return ResponseEntity.ok(availabilityService.getParkingAvailability(parkingId));
    }

    @Operation(summary = "주차장 빈자리 정보 업데이트", description = "주차장의 빈자리 수를 업데이트합니다.")
    @PutMapping("/api/parking/availability/{parkingId}")
    public ResponseEntity<ParkingAvailabilityDto> updateAvailability(
            @Parameter(description = "업데이트할 빈자리 수", required = true) @RequestBody ParkingAvailabilityDto dto,
            @Parameter(description = "주차장 ID", required = true) @PathVariable Long parkingId) {
        return ResponseEntity.ok(availabilityService.updateParkingAvailability(parkingId, dto.getEmptySpots()));
    }
    @Operation(summary = "주차장 정보 목록 조회", description = "주차장 정보 목록 조회합니다.")
    @GetMapping("/api/parking/availability")
    public List<ParkingAvailabilityDto> getAllParkingAvailability() {
        return availabilityService.getAll();
    }
    @Operation(summary = "주차장 빈자리 정보 삭제", description = "특정 주차장의 빈자리 정보를 삭제합니다.")
    @DeleteMapping("/api/parking/availability/{parkingId}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long parkingId) {
        availabilityService.deleteParkingAvailability(parkingId);
        return ResponseEntity.noContent().build();
    }

}

