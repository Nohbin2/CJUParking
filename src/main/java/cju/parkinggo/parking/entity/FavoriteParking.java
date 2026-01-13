package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자의 주차장 즐겨찾기 정보 엔티티
 */
@Entity
@Table(name = "favorite_parking")
@Getter @Setter @NoArgsConstructor
public class FavoriteParking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // 즐겨찾기한 사용자 ID

    @Column(name = "parking_id")
    private Long parkingId; // 즐겨찾기한 주차장 ID
}
