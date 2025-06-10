package cju.parkinggo.parking.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ParkingFcmSendDto {
    private String token;
    private String parkingLotName;
}
