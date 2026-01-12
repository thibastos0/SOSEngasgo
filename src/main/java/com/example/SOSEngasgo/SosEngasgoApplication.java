package com.example.SOSEngasgo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SosEngasgoApplication {

	public static void main(String[] args) {
		
		// Obtém o diretório atual da execução
    	String projectDir = System.getProperty("user.dir");
    	System.out.println("Working dir: " + projectDir);

        Dotenv dotenv = Dotenv.configure()
                .directory(projectDir)   // usa o diretório da execução
				.filename(".env")
                .ignoreIfMissing()       // evita erro quando o .env não existir (Render)
                .load();

        Map<String, Object> props = new HashMap<>();

        dotenv.entries().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            props.put(key, value);
            //System.out.println("✅ " + key + " carregada via .env");
        });

        String mongoUri = dotenv.get("MONGODB_URI");

        if (mongoUri != null) {
            System.setProperty("MONGODB_URI", mongoUri);
            System.out.println("✅ MONGODB_URI carregada via .env");
        } else {
            System.out.println("⚠️ MONGODB_URI não encontrada no .env — usando variável do Render");
        }

		//SpringApplication.run(SosEngasgoApplication.class);
        SpringApplication app = new SpringApplication(SosEngasgoApplication.class);
        app.setDefaultProperties(props);
        app.run(args);
	}

}
