package cju.parkinggo.parking.controller;

import cju.parkinggo.parking.entity.FavoriteParking;
import cju.parkinggo.parking.entity.Parking;
import cju.parkinggo.parking.entity.User;
import cju.parkinggo.parking.repository.FavoriteParkingRepository;
import cju.parkinggo.parking.repository.ParkingRepository;
import cju.parkinggo.parking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 사용자의 주차장 즐겨찾기 등록/해제 API
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoriteParkingController {

    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final FavoriteParkingRepository favoriteParkingRepository;

    public FavoriteParkingController(UserRepository userRepository,
                                     ParkingRepository parkingRepository,
                                     FavoriteParkingRepository favoriteParkingRepository) {
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.favoriteParkingRepository = favoriteParkingRepository;
    }

    /**
     * [POST] /api/favorite/add
     * 즐겨찾기 추가 (user의 kakaoId, 주차장 이름)
     */
    @PostMapping("/add")
    public String addFavorite(@RequestParam String kakaoId, @RequestParam String parkingLotName) {
        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);
        if (userOpt.isEmpty()) return "유저 없음";

        Optional<Parking> parkingOpt = parkingRepository.findByParkingName(parkingLotName);
        if (parkingOpt.isEmpty()) return "주차장 없음";

        // 이미 즐겨찾기라면 중복 방지
        boolean exists = favoriteParkingRepository
                .findByParkingId(parkingOpt.get().getId())
                .stream()
                .anyMatch(fav -> fav.getUserId().equals(userOpt.get().getId()));
        if (exists) return "이미 즐겨찾기된 주차장입니다.";

        FavoriteParking favorite = new FavoriteParking();
        favorite.setUserId(userOpt.get().getId());
        favorite.setParkingId(parkingOpt.get().getId());
        favoriteParkingRepository.save(favorite);
        return "즐겨찾기 등록 완료";
    }

    /**
     * [DELETE] /api/favorite/remove
     * 즐겨찾기 해제 (user의 kakaoId, 주차장 이름)
     */
    @DeleteMapping("/remove")
    public String removeFavorite(@RequestParam String kakaoId, @RequestParam String parkingLotName) {
        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);
        if (userOpt.isEmpty()) return "유저 없음";

        Optional<Parking> parkingOpt = parkingRepository.findByParkingName(parkingLotName);
        if (parkingOpt.isEmpty()) return "주차장 없음";

        // 즐겨찾기 엔트리 찾기
        FavoriteParking favorite = favoriteParkingRepository
                .findByParkingId(parkingOpt.get().getId())
                .stream()
                .filter(fav -> fav.getUserId().equals(userOpt.get().getId()))
                .findFirst()
                .orElse(null);
        if (favorite == null) return "즐겨찾기에 등록된 주차장이 아닙니다.";

        favoriteParkingRepository.delete(favorite);
        return "즐겨찾기 해제 완료";
    }
    /**
     * [GET] /api/favorite/list
     * 해당 사용자의 즐겨찾기 목록 반환
     */
    @GetMapping("/list")
    public List<Parking> getFavoriteList(@RequestParam String kakaoId) {
        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);
        if (userOpt.isEmpty()) return List.of();

        Long userId = userOpt.get().getId();
        List<FavoriteParking> favoriteList = favoriteParkingRepository.findByUserId(userId);

        // 즐겨찾기한 Parking 정보 반환
        List<Long> parkingIds = favoriteList.stream().map(FavoriteParking::getParkingId).toList();
        return parkingRepository.findAllById(parkingIds);
    }

}
