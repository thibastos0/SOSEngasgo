package com.example.SOSEngasgo.telegram.service;

//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

@SuppressWarnings("deprecation")
@Service
public class TelegramService {

    private final TelegramBot telegramBot;
    private final Gson gson = new Gson();

    public TelegramService(
            TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(String body) {

        try {
            Update update = gson.fromJson(body, Update.class);
            // Processa o update conforme necessário

            if (update.message() == null || update.message().text() == null) {
                return;
            }

            String messageText = update.message().text().trim();
            Long chatId = update.message().chat().id();

            //Long chatId = update.message().from().id();
            

            if (messageText.startsWith("/start")) {
                telegramBot.execute(
                    new SendMessage(chatId, "SOSEngasgo iniciado com sucesso.")
                );
            }

            telegramBot.execute(
                new SendMessage(chatId, "Webhook ativo. Mensagem recebida: " + messageText)
            );


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
