package cju.parkinggo.parking.service;

import com.google.auth.oauth2.GoogleCredentials;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FcmV1Service {

    @Value("${firebase.project-id}")
    private String projectId;

    /**
     * FCM v1 API로 단일 토큰에 푸시 발송
     */
    public void sendNotification(String targetToken, String title, String body) throws IOException {
        String apiUrl = "https://fcm.googleapis.com/v1/projects/" + projectId + "/messages:send";

        // ✅ 환경변수에서 서비스 계정 JSON 읽기
        String serviceAccountJson = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");
        if (serviceAccountJson == null || serviceAccountJson.isBlank()) {
            throw new IOException("FIREBASE_SERVICE_ACCOUNT_JSON 환경변수가 설정되지 않았습니다.");
        }

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8)))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");
        googleCredentials.refreshIfExpired();
        String accessToken = googleCredentials.getAccessToken().getTokenValue();

        // v1 메시지 바디 포맷
        String messageJson = "{"
                + "\"message\":{"
                +     "\"token\":\"" + targetToken + "\","
                +     "\"notification\":{"
                +         "\"title\":\"" + title + "\","
                +         "\"body\":\"" + body + "\""
                +     "}"
                + "}"
                + "}";

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(
                messageJson, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json; UTF-8")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            if (response.isSuccessful()) {
                System.out.println("[FCM v1] 발송 성공: " + resp);
            } else {
                System.out.println("[FCM v1] 발송 실패: " + resp);
            }
        }
    }
}
