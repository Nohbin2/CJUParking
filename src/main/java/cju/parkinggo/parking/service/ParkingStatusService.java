package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingStatusDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingStatusService {

    private final ParkingRepository parkingRepository;

    public ParkingStatusService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public void updateStatus(List<ParkingStatusDto> statusList) {
        for (ParkingStatusDto dto : statusList) {
            Parking parking = parkingRepository.findByParkingName(dto.getParkingName())
                    .orElse(new Parking());
            parking.setParkingName(dto.getParkingName());
            parking.setTotalSpots(dto.getTotalSpots());
            parking.setEmptySpots(dto.getEmptySpots());
            parkingRepository.save(parking);
        }
    }
}
