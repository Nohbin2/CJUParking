package cju.parkinggo.parking.service;

import com.google.auth.oauth2.GoogleCredentials;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FcmV1Service {

    // 반드시 실제 Firebase 프로젝트의 ID로 입력!
    @Value("${firebase.project-id}")
    private String projectId;

    // 서비스 계정 키 경로 (src/main/resources 하위에 두는 것이 일반적)
    @Value("${firebase.service-account-file}")
    private String serviceAccountFile;

    /**
     * FCM v1 API로 단일 토큰에 푸시 발송
     */
    public void sendNotification(String targetToken, String title, String body) throws IOException {
        String apiUrl = "https://fcm.googleapis.com/v1/projects/" + projectId + "/messages:send";

        // 1. 서비스 계정으로 AccessToken 얻기
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(serviceAccountFile))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");
        googleCredentials.refreshIfExpired();
        String accessToken = googleCredentials.getAccessToken().getTokenValue();

        // 2. v1 메시지 바디 포맷 (필요시 data 항목 추가 가능)
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
