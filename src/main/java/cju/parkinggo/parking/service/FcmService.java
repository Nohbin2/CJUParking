package cju.parkinggo.parking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * FCM 푸시 알림 전송용 서비스 클래스 (OkHttp 사용)
 */
@Service
public class FcmService {

    private final String API_URL = "https://fcm.googleapis.com/fcm/send";
    @Value("${fcm.server-key}")
    private String serverKey;

    /**
     * 단일 FCM 토큰으로 알림 발송
     * @param targetToken 수신자 FCM 토큰
     * @param title 알림 제목
     * @param body 알림 내용
     */
    public void sendNotification(String targetToken, String title, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        // FCM 메시지 형태 생성
        Map<String, Object> notification = Map.of(
                "title", title,
                "body", body
        );

        Map<String, Object> bodyMap = Map.of(
                "to", targetToken,
                "notification", notification
        );

        String json = objectMapper.writeValueAsString(bodyMap);

        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader("Authorization", "key=" + serverKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("FCM 발송 결과: " + response.body().string());
        }
    }
}
