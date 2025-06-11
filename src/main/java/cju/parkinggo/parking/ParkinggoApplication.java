package cju.parkinggo.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;


@SpringBootApplication
public class ParkinggoApplication {

	public static void main(String[] args) {
		writeServiceAccountFileIfNeed();

		SpringApplication.run(ParkinggoApplication.class, args);
	}
	private static void writeServiceAccountFileIfNeed() {
		try {
			String json = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");
			if (json != null && !json.isEmpty()) {
				File f = new File("src/main/resources/service-account.json");
				// 이미 있으면 만들지 않음 (필요시 true로 강제덮어쓰기)
				if (!f.exists()) {
					f.getParentFile().mkdirs(); // 폴더도 없을 수 있으니!
					try (FileWriter fw = new FileWriter(f)) {
						fw.write(json);
					}
					System.out.println("[SERVICE-ACCOUNT] service-account.json 파일 생성 완료");
				}
			}
		} catch (Exception e) {
			System.out.println("[SERVICE-ACCOUNT] 파일 생성 중 오류: " + e.getMessage());
		}
	}
}
