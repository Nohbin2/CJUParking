package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주차장 ID

    private String parkingName; // 데이터셋 이름
    private String name;        // 주차장 이름
    private String address;     // 주소
    private double latitude;    // 위도
    private double longitude;   // 경도
    private int totalSpots;     // 총 주차 공간 수

    // ✅ 연관된 ParkingAvailability 엔티티 목록
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingAvailability> availabilities = new ArrayList<>();

    public Parking() {}

    public Parking(String parkingName, String name, String address, double latitude, double longitude, int totalSpots) {
        this.parkingName = parkingName;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalSpots = totalSpots;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getParkingName() { return parkingName; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getTotalSpots() { return totalSpots; }
    public List<ParkingAvailability> getAvailabilities() { return availabilities; }

    public void setId(Long id) { this.id = id; }
    public void setParkingName(String parkingName) { this.parkingName = parkingName; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setTotalSpots(int totalSpots) { this.totalSpots = totalSpots; }
    public void setAvailabilities(List<ParkingAvailability> availabilities) { this.availabilities = availabilities; }
}
