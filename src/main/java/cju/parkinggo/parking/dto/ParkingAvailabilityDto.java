package cju.parkinggo.parking.dto;

import cju.parkinggo.parking.entity.ParkingAvailability;

import java.time.LocalDateTime;

public class ParkingAvailabilityDto {
    private Long parkingId;
    private int emptySpots;
    private LocalDateTime updatedAt;

    public ParkingAvailabilityDto() {}

    public ParkingAvailabilityDto(Long parkingId, int emptySpots, LocalDateTime updatedAt) {
        this.parkingId = parkingId;
        this.emptySpots = emptySpots;
        this.updatedAt = updatedAt;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public int getEmptySpots() {
        return emptySpots;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public void setEmptySpots(int emptySpots) {
        this.emptySpots = emptySpots;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ✅ 정적 메서드 추가 (에러 해결용)
    public static ParkingAvailabilityDto fromEntity(ParkingAvailability entity) {
        return new ParkingAvailabilityDto(
                entity.getParking().getId(),
                entity.getEmptySpots(),
                entity.getUpdatedAt()
        );
    }
}
