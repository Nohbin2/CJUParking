package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingStatusDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingStatusService {

    private final ParkingRepository parkingRepository;
    private final ParkingAvailabilityRepository availabilityRepository;

    public ParkingStatusService(ParkingRepository parkingRepository,
                                ParkingAvailabilityRepository availabilityRepository) {
        this.parkingRepository = parkingRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public void updateStatus(List<ParkingStatusDto> statusList) {
        for (ParkingStatusDto dto : statusList) {
            Parking parking = parkingRepository.findByParkingName(dto.getParkingName())
                    .orElseThrow(() -> new RuntimeException("주차장 이름이 존재하지 않습니다: " + dto.getParkingName()));

            // 총 자리 수 업데이트
            parking.setTotalSpots(dto.getTotalSpots());
            parkingRepository.save(parking);

            // 기존 availability를 찾아서 갱신 or 새로 생성
            ParkingAvailability availability = availabilityRepository.findTopByParkingIdOrderByUpdatedAtDesc(parking.getId());

            if (availability != null) {
                availability.setEmptySpots(dto.getEmptySpots());
                availability.setUpdatedAt(LocalDateTime.now());
            } else {
                availability = new ParkingAvailability(
                        parking,
                        dto.getEmptySpots(),
                        LocalDateTime.now()
                );
            }
            availabilityRepository.save(availability);
        }
    }
}
