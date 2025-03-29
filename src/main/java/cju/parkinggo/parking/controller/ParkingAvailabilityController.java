package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.service.ParkingAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
