package cju.parkinggo.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String fcmToken;

    // 생성자 추가 가능
    public User(String username, String fcmToken) {
        this.username = username;
        this.fcmToken = fcmToken;
    }
}
