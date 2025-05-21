package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.dto.ParkingAvailabilityCreateDto;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingAvailabilityService {

    private final ParkingRepository parkingRepository;
    private final ParkingAvailabilityRepository availabilityRepository;

    public ParkingAvailabilityService(ParkingRepository parkingRepository,
                                      ParkingAvailabilityRepository availabilityRepository) {
        this.parkingRepository = parkingRepository;
        this.availabilityRepository = availabilityRepository;
    }

    // ✅ 주차장 ID로 가장 최신 빈자리 정보 조회
    public ParkingAvailabilityDto getParkingAvailability(Long parkingId) {
        ParkingAvailability availability = availabilityRepository
                .findTopByParkingIdOrderByUpdatedAtDesc(parkingId);

        if (availability == null) {
            throw new RuntimeException("해당 주차장에 대한 빈자리 정보가 없습니다.");
        }

        return new ParkingAvailabilityDto(
                parkingId,
                availability.getEmptySpots(),
                availability.getUpdatedAt()
        );
    }

    // ✅ 빈자리 정보 업데이트
    public ParkingAvailabilityDto updateParkingAvailability(Long parkingId, int emptySpots) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        ParkingAvailability availability = new ParkingAvailability(
                parking,
                emptySpots,
                LocalDateTime.now()
        );

        availabilityRepository.save(availability);

        return new ParkingAvailabilityDto(parkingId, emptySpots, availability.getUpdatedAt());
    }

    // ✅ 전체 주차장 빈자리 정보 조회
    public List<ParkingAvailabilityDto> getAll() {
        return availabilityRepository.findAll().stream()
                .map(a -> new ParkingAvailabilityDto(
                        a.getParking().getId(),
                        a.getEmptySpots(),
                        a.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // ✅ 주차장 생성 시 초기 빈자리 추가용 (필요 시)
    public void createInitialAvailability(Parking parking, int initialSpots) {
        ParkingAvailability availability = new ParkingAvailability(
                parking,
                initialSpots,
                LocalDateTime.now()
        );
        availabilityRepository.save(availability);
    }
    public List<ParkingAvailabilityDto> getLatestAvailabilityList() {
        return availabilityRepository.findLatestAvailabilityPerParking().stream()
                .map(ParkingAvailabilityDto::fromEntity)
                .toList();
    }
}
