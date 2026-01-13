package com.example.SOSEngasgo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//import jakarta.annotation.PostConstruct;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot-token}")
    private String token;

    @Value("${telegram.webhook-url}")
    private String webhookUrl;

    @Value("${telegram.chat-id-user}")
    private Long userChatId;

    @Value("${telegram.chat-id-group}")
    private Long groupChatId;

    public String getToken() {
        return token;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public Long getUserChatId() {
        return userChatId;
    }

    public Long getGroupChatId() {
        return groupChatId;
    }
/*
    @PostConstruct
    public void debug() {
        System.out.println("Telegram token carregado? " + (token != null));
    }*/

}
