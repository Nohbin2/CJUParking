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
        return new ParkingDto(
                parking.getId(),
                parking.getParkingName(),
                parking.getName(),
                parking.getAddress(),
                parking.getLatitude(),
                parking.getLongitude(),
                parking.getTotalSpots()
        );
    }

    public ParkingDto createParking(ParkingDto dto) {
        // 1. Parking 저장
        Parking parking = new Parking();
        parking.setParkingName(dto.getParkingName());
        parking.setName(dto.getName());
        parking.setAddress(dto.getAddress());
        parking.setLatitude(dto.getLatitude());
        parking.setLongitude(dto.getLongitude());
        parking.setTotalSpots(dto.getTotalSpots());
        parkingRepository.save(parking);

        // 2. ParkingAvailability 초기화
        ParkingAvailability availability = new ParkingAvailability(
                parking,
                0, // 초기 빈자리 수
                LocalDateTime.now()
        );
        availabilityRepository.save(availability);

        return new ParkingDto(
                parking.getId(),
                parking.getParkingName(),
                parking.getName(),
                parking.getAddress(),
                parking.getLatitude(),
                parking.getLongitude(),
                parking.getTotalSpots()
        );
    }

    public List<ParkingDto> getAllParking() {
        return parkingRepository.findAll().stream()
                .map(p -> new ParkingDto(
                        p.getId(),
                        p.getParkingName(),
                        p.getName(),
                        p.getAddress(),
                        p.getLatitude(),
                        p.getLongitude(),
                        p.getTotalSpots()
                ))
                .collect(Collectors.toList());
    }

    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));
        parkingRepository.delete(parking);
    }
}
