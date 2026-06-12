package com.example.SOSEngasgo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.base64}")
    private String credentialsBase64;

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            byte[] decoded = java.util.Base64.getDecoder().decode(credentialsBase64.trim());

            InputStream stream = new ByteArrayInputStream(decoded);

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

            FirebaseApp.initializeApp(options);
        }
    }
}