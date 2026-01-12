package com.example.SOSEngasgo.telegram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SOSEngasgo.config.TelegramBotConfig;
import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotBeanConfig {

    @Bean
    public TelegramBot telegramBot(TelegramBotConfig telegramBotConfig) {
        return new TelegramBot(telegramBotConfig.getToken());
    }

}
