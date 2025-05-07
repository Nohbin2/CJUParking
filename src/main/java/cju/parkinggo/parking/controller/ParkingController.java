package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
