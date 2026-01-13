package cju.parkinggo.parking.dto;

public class ParkingDto {

    private Long id;
    private String parkingName;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int totalSpots;

    public ParkingDto() {}

    public ParkingDto(Long id, String parkingName, String name, String address,
                      double latitude, double longitude, int totalSpots) {
        this.id = id;
        this.parkingName = parkingName;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalSpots = totalSpots;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getParkingName() { return parkingName; }
    public void setParkingName(String parkingName) { this.parkingName = parkingName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getTotalSpots() { return totalSpots; }
    public void setTotalSpots(int totalSpots) { this.totalSpots = totalSpots; }
}
