package com.example.SOSEngasgo.telegram.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

@SuppressWarnings("deprecation")
@Service
public class TelegramService {

    private final TelegramBot telegramBot;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TelegramService(
            TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(String body) {

        try {
            Update update = objectMapper.readValue(body, Update.class);
            // Processa o update conforme necessário

            if (update.message() == null || update.message().text() == null) {
                return;
            }
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            if ("/start".equals(messageText.trim())) {
                telegramBot.execute(
                    new SendMessage(chatId, "SOSEngasgo iniciado com sucesso.")
                );
            
            telegramBot.execute(
                new SendMessage(chatId, "Webhook ativo. Mensagem recebida: " + messageText)
            );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
