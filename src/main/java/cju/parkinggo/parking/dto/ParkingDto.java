package cju.parkinggo.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ParkingDto {
    private Long id;
    private String name;
    private String location;
    private int totalSpots;

    public ParkingDto(Long id, String name, String location, int totalSpots) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.totalSpots = totalSpots;
    }

    // Getter 메서드 추가
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalSpots() {
        return totalSpots;
    }
}
