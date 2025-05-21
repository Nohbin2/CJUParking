package cju.parkinggo.parking.dto;

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

    public Long getParkingId() { return parkingId; }
    public void setParkingId(Long parkingId) { this.parkingId = parkingId; }

    public int getEmptySpots() { return emptySpots; }
    public void setEmptySpots(int emptySpots) { this.emptySpots = emptySpots; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
