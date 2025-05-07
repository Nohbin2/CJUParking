package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkingAvailabilityRepository availabilityRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository,
                          ParkingAvailabilityRepository availabilityRepository) {
        this.parkingRepository = parkingRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public ParkingDto getParking(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주차장 정보를 찾을 수 없습니다."));
        return new ParkingDto(parking.getId(), parking.getName(), parking.getLocation(), parking.getTotalSpots());
    }

    public ParkingDto createParking(ParkingDto dto) {
        // 1. Parking 저장
        Parking parking = new Parking();
        parking.setName(dto.getName());
        parking.setLocation(dto.getLocation());
        parking.setTotalSpots(dto.getTotalSpots());
        parkingRepository.save(parking);

        // 2. ParkingAvailability 초기화 → 빈자리 수는 totalSpots로 동일하게 설정하거나 0
        ParkingAvailability availability = new ParkingAvailability(
                parking,
                0, // 초기 빈자리 수 0개
                LocalDateTime.now()
        );
        availabilityRepository.save(availability);

        return new ParkingDto(parking.getId(), parking.getName(), parking.getLocation(), parking.getTotalSpots());
    }

    public List<ParkingDto> getAllParking() {
        return parkingRepository.findAll().stream()
                .map(p -> new ParkingDto(p.getId(), p.getName(), p.getLocation(), p.getTotalSpots()))
                .collect(Collectors.toList());
    }
    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        parkingRepository.delete(parking);
    }

}
