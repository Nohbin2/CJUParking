package cju.parkinggo.parking.dto;

public class ParkingStatusDto {
    private String parkingName;
    private int totalSpots;
    private int emptySpots;

    public ParkingStatusDto() {}

    public ParkingStatusDto(String parkingName, int totalSpots, int emptySpots) {
        this.parkingName = parkingName;
        this.totalSpots = totalSpots;
        this.emptySpots = emptySpots;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getEmptySpots() {
        return emptySpots;
    }

    public void setEmptySpots(int emptySpots) {
        this.emptySpots = emptySpots;
    }
}
