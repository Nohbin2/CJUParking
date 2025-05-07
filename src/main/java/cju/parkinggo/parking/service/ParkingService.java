package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public ParkingDto getParking(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주차장 정보를 찾을 수 없습니다."));
        return new ParkingDto(parking.getId(), parking.getName(), parking.getLocation(), parking.getTotalSpots());
    }

    public ParkingDto createParking(ParkingDto dto) {
        Parking parking = new Parking();
        parking.setName(dto.getName());
        parking.setLocation(dto.getLocation());
        parking.setTotalSpots(dto.getTotalSpots());

        Parking saved = parkingRepository.save(parking);
        return new ParkingDto(saved.getId(), saved.getName(), saved.getLocation(), saved.getTotalSpots());
    }
    public List<ParkingDto> getAllParking() {
        return parkingRepository.findAll().stream()
                .map(p -> new ParkingDto(p.getId(), p.getName(), p.getLocation(), p.getTotalSpots()))
                .collect(Collectors.toList());
    }
}
