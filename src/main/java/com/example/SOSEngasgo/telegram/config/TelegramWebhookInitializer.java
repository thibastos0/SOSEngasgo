package com.example.SOSEngasgo.telegram.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.SOSEngasgo.config.TelegramBotConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetWebhook;

@Component
public class TelegramWebhookInitializer implements CommandLineRunner {

    private final TelegramBot telegramBot;
    private final TelegramBotConfig telegramBotConfig;

    public TelegramWebhookInitializer(
            TelegramBot telegramBot,
            TelegramBotConfig telegramBotConfig) {
        this.telegramBot = telegramBot;
        this.telegramBotConfig = telegramBotConfig;
    }

    @Override
    public void run(String... args) {
        telegramBot.execute(
            new SetWebhook().url(telegramBotConfig.getWebhookUrl())
        );
    }
}
