package cju.parkinggo.parking.service;

import cju.parkinggo.parking.entity.FavoriteParking;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.FavoriteParkingRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 주차장 빈자리 발생 시, 즐겨찾기 사용자에게 알림을 발송하는 서비스
 */
@Service
public class ParkingNotificationService {

    private final ParkingRepository parkingRepository;
    private final FavoriteParkingRepository favoriteParkingRepository;
    private final UserRepository userRepository;
    private final FcmService fcmService;

    public ParkingNotificationService(
            ParkingRepository parkingRepository,
            FavoriteParkingRepository favoriteParkingRepository,
            UserRepository userRepository,
            FcmService fcmService) {
        this.parkingRepository = parkingRepository;
        this.favoriteParkingRepository = favoriteParkingRepository;
        this.userRepository = userRepository;
        this.fcmService = fcmService;
    }

    /**
     * 특정 주차장에 빈자리 발생 시, 즐겨찾기한 유저 모두에게 알림 전송
     * @param parkingLotName 빈자리 생긴 주차장 이름
     */
    @Transactional
    public void notifyUsersWhenParkingAvailable(String parkingLotName) {
        Parking parking = parkingRepository.findByParkingName(parkingLotName)
                .orElseThrow(() -> new RuntimeException("주차장 없음: " + parkingLotName));

        List<FavoriteParking> favorites = favoriteParkingRepository.findByParkingId(parking.getId());

        for (FavoriteParking fav : favorites) {
            Optional<User> userOpt = userRepository.findById(fav.getUserId());
            userOpt.ifPresent(user -> {
                String fcmToken = user.getFcmToken();
                if (fcmToken != null && !fcmToken.isEmpty()) {
                    try {
                        fcmService.sendNotification(
                                fcmToken,
                                "주차장 빈자리 알림",
                                parkingLotName + "에 빈자리가 생겼습니다!"
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
