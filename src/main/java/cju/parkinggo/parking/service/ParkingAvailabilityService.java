package cju.parkinggo.parking.service;

import cju.parkinggo.parking.dto.ParkingAvailabilityDto;
import cju.parkinggo.parking.entity.FavoriteParking;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.ParkingAvailability;
import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.FavoriteParkingRepository;
import cju.parkinggo.parking.repository.ParkingAvailabilityRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingAvailabilityService {

    private final ParkingRepository parkingRepository;
    private final ParkingAvailabilityRepository availabilityRepository;
    private final FavoriteParkingRepository favoriteRepo;
    private final UserRepository userRepository;
    private final FcmV1Service fcmV1Service;   // << 기존 FcmService 대신 FcmV1Service로!

    public ParkingAvailabilityService(
            ParkingRepository parkingRepository,
            ParkingAvailabilityRepository availabilityRepository,
            FavoriteParkingRepository favoriteRepo,
            UserRepository userRepository,
            FcmV1Service fcmV1Service // << 생성자에 FcmV1Service 주입
    ) {
        this.parkingRepository = parkingRepository;
        this.availabilityRepository = availabilityRepository;
        this.favoriteRepo = favoriteRepo;
        this.userRepository = userRepository;
        this.fcmV1Service = fcmV1Service; // << 필드 할당
    }

    // ✅ 주차장 ID로 가장 최신 빈자리 정보 조회
    public ParkingAvailabilityDto getParkingAvailability(Long parkingId) {
        ParkingAvailability availability = availabilityRepository
                .findTopByParkingIdOrderByUpdatedAtDesc(parkingId);

        if (availability == null) {
            throw new RuntimeException("해당 주차장에 대한 빈자리 정보가 없습니다.");
        }

        return new ParkingAvailabilityDto(
                parkingId,
                availability.getEmptySpots(),
                availability.getUpdatedAt()
        );
    }

    // ✅ 빈자리 정보 업데이트 + 알림 전송
    public ParkingAvailabilityDto updateParkingAvailability(Long parkingId, int emptySpots) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다."));

        // 이전 빈자리 정보 가져오기
        ParkingAvailability latest = availabilityRepository
                .findTopByParkingIdOrderByUpdatedAtDesc(parkingId);
        int previousSpots = (latest != null) ? latest.getEmptySpots() : 0;

        // 새 정보 저장
        ParkingAvailability availability = new ParkingAvailability(
                parking,
                emptySpots,
                LocalDateTime.now()
        );
        availabilityRepository.save(availability);

        // ✅ 빈자리 증가 시 즐겨찾기 유저에게 알림 전송
        if (emptySpots > previousSpots) {
            List<FavoriteParking> favorites = favoriteRepo.findByParkingId(parkingId);
            List<String> tokens = favorites.stream()
                    .map(fav -> {
                        User user = userRepository.findById(fav.getUserId()).orElse(null);
                        return (user != null) ? user.getFcmToken() : null;
                    })
                    .filter(token -> token != null && !token.isBlank())
                    .toList();

            for (String token : tokens) {
                try {
                    // 기존 fcmService → fcmV1Service로!
                    fcmV1Service.sendNotification(token, "주차장 알림", "즐겨찾기한 주차장에 빈자리가 생겼습니다!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return new ParkingAvailabilityDto(parkingId, emptySpots, availability.getUpdatedAt());
    }

    // ✅ 전체 주차장 빈자리 정보 조회
    public List<ParkingAvailabilityDto> getAll() {
        return availabilityRepository.findAll().stream()
                .map(a -> new ParkingAvailabilityDto(
                        a.getParking().getId(),
                        a.getEmptySpots(),
                        a.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // ✅ 주차장 생성 시 초기 빈자리 추가용
    public void createInitialAvailability(Parking parking, int initialSpots) {
        ParkingAvailability availability = new ParkingAvailability(
                parking,
                initialSpots,
                LocalDateTime.now()
        );
        availabilityRepository.save(availability);
    }

    // ✅ 주차장별 최신 빈자리 정보 리스트
    public List<ParkingAvailabilityDto> getLatestAvailabilityList() {
        return availabilityRepository.findLatestAvailabilityPerParking().stream()
                .map(ParkingAvailabilityDto::fromEntity)
                .toList();
    }
}
