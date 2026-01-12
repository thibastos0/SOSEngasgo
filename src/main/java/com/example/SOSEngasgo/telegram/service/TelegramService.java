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

        System.out.println("Recebido update do Telegram: " + body);

        try {
            Update update = objectMapper.readValue(body, Update.class);
            // Processa o update conforme necessário

            if (update.message() == null || update.message().text() == null) {
                System.out.println("Update sem mensagem de texto, ignorando.");
                return;
            }
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            System.out.println("Mensagem recebida: " + messageText + " do chatId: " + chatId);

            if ("/start".equals(messageText.trim())) {
                telegramBot.execute(
                    new SendMessage(chatId, "SOSEngasgo iniciado com sucesso.")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
