package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주차장 ID

    private String name;  // 주차장 이름
    private String location;  // 위치
    private int totalSpots;  // 총 주차 공간 수

    // ✅ 연관된 ParkingAvailability 엔티티 목록 (삭제 시 cascade 처리)
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingAvailability> availabilities = new ArrayList<>();

    // Getter 메서드
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getTotalSpots() { return totalSpots; }

    // Setter 메서드
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setTotalSpots(int totalSpots) { this.totalSpots = totalSpots; }

    // 연관 리스트 접근자 (필요 시)
    public List<ParkingAvailability> getAvailabilities() { return availabilities; }
    public void setAvailabilities(List<ParkingAvailability> availabilities) { this.availabilities = availabilities; }
}
