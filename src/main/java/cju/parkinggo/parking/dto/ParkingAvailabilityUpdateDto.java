package cju.parkinggo.parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingAvailabilityUpdateDto {
    private Long parkingId;
    private int availableSpots;
}
