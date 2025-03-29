package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
