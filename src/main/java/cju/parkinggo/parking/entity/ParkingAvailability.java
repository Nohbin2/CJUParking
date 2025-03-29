package cju.parkinggo.parking.entity;

import cju.parkinggo.parking.entity.Parking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class ParkingAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private Parking parking;

    private int emptySpots;

    private LocalDateTime updatedAt;

    // 기본 생성자
    public ParkingAvailability() {}

    // 생성자
    public ParkingAvailability(Parking parking, int emptySpots, LocalDateTime updatedAt) {
        this.parking = parking;
        this.emptySpots = emptySpots;
        this.updatedAt = updatedAt;
    }

    // getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public int getEmptySpots() {
        return emptySpots;
    }

    public void setEmptySpots(int emptySpots) {
        this.emptySpots = emptySpots;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}