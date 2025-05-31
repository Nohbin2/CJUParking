package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingController(ParkingService parkingService, ParkingRepository parkingRepository) {
        this.parkingService = parkingService;
        this.parkingRepository = parkingRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> getParking(@PathVariable Long id) {
        return ResponseEntity.ok(parkingService.getParking(id));
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

    @PutMapping("/name/{parkingName}")
    public ResponseEntity<?> updateByParkingName(
            @PathVariable String parkingName,
            @RequestBody Map<String, Object> body) {

        Parking parking = parkingRepository.findByParkingName(parkingName)
                .orElseThrow(() -> new RuntimeException("해당 주차장이 존재하지 않습니다: " + parkingName));

        if (body.containsKey("name")) parking.setName((String) body.get("name"));
        if (body.containsKey("address")) parking.setAddress((String) body.get("address"));
        if (body.containsKey("latitude")) parking.setLatitude(Double.parseDouble(body.get("latitude").toString()));
        if (body.containsKey("longitude")) parking.setLongitude(Double.parseDouble(body.get("longitude").toString()));
        if (body.containsKey("totalSpots")) parking.setTotalSpots(Integer.parseInt(body.get("totalSpots").toString()));

        parkingRepository.save(parking);

        return ResponseEntity.ok().body("수정 완료: " + parkingName);
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @RequestBody Parking updatedData) {

        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 주차장이 존재하지 않습니다: " + id));

        // 모든 필드 덮어쓰기
        parking.setParkingName(updatedData.getParkingName());
        parking.setName(updatedData.getName());
        parking.setAddress(updatedData.getAddress());
        parking.setLatitude(updatedData.getLatitude());
        parking.setLongitude(updatedData.getLongitude());
        parking.setTotalSpots(updatedData.getTotalSpots());

        parkingRepository.save(parking);

        return ResponseEntity.ok().body("수정 완료: ID " + id);
    }

}
