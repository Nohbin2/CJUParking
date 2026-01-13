package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 주차장 기본 정보 엔티티
 */
@Entity
@Table(name = "parking")
@Getter @Setter @NoArgsConstructor
public class Parking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_name")
    private String parkingName; // 주차장 이름(프론트·알림에서 사용)

    private String name;    // 소유 기관, 학교 등
    private String address; // 주소
    private Double latitude;
    private Double longitude;

    @Column(name = "total_spots")
    private Integer totalSpots; // 총 주차면 수
}
