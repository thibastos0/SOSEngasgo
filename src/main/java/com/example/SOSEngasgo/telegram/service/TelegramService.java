package com.example.SOSEngasgo.telegram.service;

//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.example.SOSEngasgo.config.TelegramBotConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

@SuppressWarnings("deprecation")
@Service
public class TelegramService {

    private final TelegramBot telegramBot;
    private final TelegramBotConfig telegramBotConfig;
    private final Gson gson = new Gson();

    public TelegramService(
            TelegramBot telegramBot,
            TelegramBotConfig telegramBotConfig) {
        this.telegramBot = telegramBot;
        this.telegramBotConfig = telegramBotConfig;
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

            if (messageText.startsWith("/start")) {
                telegramBot.execute(
                    new SendMessage(chatId, "SOSEngasgo iniciado com sucesso.")
                );
            } //eco de mensagem (para teste)
            /*else {

                telegramBot.execute(
                    new SendMessage(chatId, "Webhook ativo. Mensagem recebida: " + messageText)
                );
            }*/
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void enviarAlertaEmergencia(String emailUsuario, double latitude, double longitude) {
        String mapLink = "https://maps.google.com/?q=" + latitude + "," + longitude;

        String mensagem = "🚨 *EMERGÊNCIA - ENGASGO*\n\n"
            + "👤 Usuário: " + emailUsuario + "\n"
            + "📍 Localização: [Ver no Maps](" + mapLink + ")\n"
            + "⏰ " + java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));


        SendResponse response = telegramBot.execute(
            new SendMessage(telegramBotConfig.getUserChatId(), mensagem)
                .parseMode(com.pengrad.telegrambot.model.request.ParseMode.Markdown)
        );

        if (!response.isOk()) {
            throw new RuntimeException("Erro ao enviar mensagem para Telegram: " + response.description());
        }

    }

}
