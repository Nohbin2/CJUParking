package cju.parkinggo.parking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class FcmService {

    private final String API_URL = "https://fcm.googleapis.com/fcm/send";
    @Value("${fcm.server-key}")
    private String serverKey;

    public void sendNotification(String targetToken, String title, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

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
            System.out.println("FCM 응답: " + response.body().string());
        }
    }
}
