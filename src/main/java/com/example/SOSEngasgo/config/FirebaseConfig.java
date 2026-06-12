package com.example.SOSEngasgo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
//import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            // Tenta Secret File do Render primeiro, depois fallback para variável de ambiente
            InputStream stream;
            java.io.File secretFile = new java.io.File("/etc/secrets/firebase-service-account.json");

            if (secretFile.exists()) {
                stream = new java.io.FileInputStream(secretFile);
            } else {
                // fallback local: lê do classpath (src/main/resources)
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