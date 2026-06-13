package com.example.SOSEngasgo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            InputStream stream = null;

            // 1. Tenta Secret File do Render
            java.io.File secretFile = new java.io.File("/etc/secrets/firebase-service-account.json");
            if (secretFile.exists()) {
                stream = new java.io.FileInputStream(secretFile);
            }

            // 2. Tenta variável de ambiente Base64 (Railway)
            if (stream == null) {
                String base64 = System.getenv("FIREBASE_CREDENTIALS_BASE64");
                if (base64 != null && !base64.isBlank()) {
                    byte[] decoded = java.util.Base64.getDecoder().decode(base64.trim());
                    stream = new ByteArrayInputStream(decoded);
                }
            }

            // 3. Tenta classpath (desenvolvimento local)
            if (stream == null) {
                stream = getClass().getClassLoader()
                    .getResourceAsStream("firebase-service-account.json");
            }

            if (stream == null) {
                throw new IllegalStateException("firebase-service-account.json não encontrado!");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

            FirebaseApp.initializeApp(options);
        }
    }
}