package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class ParkingAvailabilityService {
    private final ParkingAvailabilityRepository availabilityRepository;
    public ParkingAvailabilityService(ParkingAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public ParkingAvailabilityDto getParkingAvailability(Long parkingId) {
        ParkingAvailability availability = availabilityRepository.findByParkingId(parkingId)
                .orElseThrow(() -> new RuntimeException("해당 주차장의 빈자리가 없습니다."));
        return new ParkingAvailabilityDto(availability.getParking().getId(), availability.getEmptySpots(), availability.getUpdatedAt());
    }

    public ParkingAvailabilityDto updateParkingAvailability(Long parkingId, int emptySpots) {
        ParkingAvailability availability = availabilityRepository.findByParkingId(parkingId)
                .orElseThrow(() -> new RuntimeException("해당 주차장의 빈자리가 없습니다."));
        availability.setEmptySpots(emptySpots);
        availability.setUpdatedAt(LocalDateTime.now());
        availabilityRepository.save(availability);

        return new ParkingAvailabilityDto(availability.getParking().getId(), availability.getEmptySpots(), availability.getUpdatedAt());
    }
}
