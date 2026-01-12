package com.example.SOSEngasgo.telegram.service;

import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.config.TelegramBotConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

@SuppressWarnings("deprecation")
@Service
public class TelegramService {

    private final TelegramBot telegramBot;
    private final TelegramBotConfig telegramBotConfig;

    public TelegramService(
            TelegramBot telegramBot,
            TelegramBotConfig telegramBotConfig) {
        this.telegramBot = telegramBot;
        this.telegramBotConfig = telegramBotConfig;
    }

    public void processUpdate(Update update) {

        if (update.message() == null || update.message().text() == null) {
            return;
        }

        String text = update.message().text();

        if ("/start".equals(text)) {
            telegramBot.execute(
                new SendMessage(telegramBotConfig.getUserChatId(), "SOSEngasgo iniciado com sucesso.")
            );
        }
    }
}
