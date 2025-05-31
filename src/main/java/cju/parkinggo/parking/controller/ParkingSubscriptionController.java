package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingSubscription;
import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.repository.ParkingSubscriptionRepository;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
public class ParkingSubscriptionController {

    private final ParkingSubscriptionRepository subscriptionRepo;
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;

    public ParkingSubscriptionController(ParkingSubscriptionRepository subscriptionRepo,
                                         UserRepository userRepository,
                                         ParkingRepository parkingRepository) {
        this.subscriptionRepo = subscriptionRepo;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestParam Long userId, @RequestParam Long parkingId) {
        if (subscriptionRepo.existsByUserIdAndParkingId(userId, parkingId)) {
            return ResponseEntity.badRequest().body("이미 구독 중입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장 없음"));

        ParkingSubscription subscription = new ParkingSubscription();
        subscription.setUser(user);
        subscription.setParking(parking);
        subscriptionRepo.save(subscription);

        return ResponseEntity.ok("구독 완료");
    }

    @DeleteMapping
    public ResponseEntity<?> unsubscribe(@RequestParam Long userId, @RequestParam Long parkingId) {
        if (!subscriptionRepo.existsByUserIdAndParkingId(userId, parkingId)) {
            return ResponseEntity.badRequest().body("구독 중이 아닙니다.");
        }

        subscriptionRepo.deleteByUserIdAndParkingId(userId, parkingId);
        return ResponseEntity.ok("구독 해제 완료");
    }
}
