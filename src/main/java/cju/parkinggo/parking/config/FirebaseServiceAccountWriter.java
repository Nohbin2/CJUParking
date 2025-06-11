package cju.parkinggo.parking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FirebaseServiceAccountWriter {
    @Value("${firebase.service-account-file}")
    private String serviceAccountFile;

    @Value("${FIREBASE_SERVICE_ACCOUNT_JSON:}")
    private String serviceAccountJson;

    @PostConstruct
    public void writeServiceAccountFile() {
        if (serviceAccountJson == null || serviceAccountJson.isBlank()) {
            System.err.println("FIREBASE_SERVICE_ACCOUNT_JSON env not set.");
            return;
        }
        try (FileWriter writer = new FileWriter(serviceAccountFile)) {
            writer.write(serviceAccountJson);
            System.out.println("✅ service-account.json written to " + serviceAccountFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
