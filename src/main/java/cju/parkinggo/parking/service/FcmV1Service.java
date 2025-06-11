package cju.parkinggo.parking.service;

import com.google.auth.oauth2.GoogleCredentials;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FcmV1Service {

    @Value("${firebase.project-id}")
    private String projectId;

    @Value("${firebase.service-account-file}")
    private String serviceAccountFile;

    /**
     * FCM v1 API로 단일 토큰에 푸시 발송
     */
    public void sendNotification(String targetToken, String title, String body) throws IOException {
        String apiUrl = "https://fcm.googleapis.com/v1/projects/" + projectId + "/messages:send";

        // ✅ resources 폴더에서 리소스 스트림으로 파일 읽기 (배포/JAR 환경 호환)
        InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream(serviceAccountFile);
        if (serviceAccountStream == null) {
            throw new IOException("Firebase service account file not found: " + serviceAccountFile);
        }

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(serviceAccountStream)
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
