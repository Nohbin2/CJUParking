package cju.parkinggo.parking.entity;

import jakarta.persistence.*;

@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주차장 ID
    private String name;  // 주차장 이름
    private String location;  // 위치
    private int totalSpots;  // 총 주차 공간 수

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

    // Setter 메서드 추가 (필요 시)
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }
}

