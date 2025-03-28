package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Random;


public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String playerMove = update.getMessage().getText().toLowerCase();

            if (playerMove.equals("камень") || playerMove.equals("ножницы") || playerMove.equals("бумага")) {
                String[] options = {"Камень", "Бумага", "Ножницы"};
                Random random = new Random();
                int number = random.nextInt(3);

                String botMove = options[number].toLowerCase();
                sendMessage(update, botMove);

                if (playerMove.equals(botMove)) {
                    sendMessage(update, "Ничья!");
                } else if (
                                (playerMove.equals("камень") && botMove.equals("ножницы")) ||
                                (playerMove.equals("бумага") && botMove.equals("камень")) ||
                                (playerMove.equals("ножницы") && botMove.equals("бумага"))
                ) {
                    sendMessage(update, "Ты победил! 🎉");
                } else {
                    sendMessage(update, "Ты проиграл! 😢");
                }
            }
        }
    }

    public void sendMessage(Update update, String text) {
        String chatId = update.getMessage().getChatId().toString();
        SendMessage response = new SendMessage();

        response.setChatId(chatId);
        response.setText(text);

        try {
            execute(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "RPSBot";
    }

    @Override
    public String getBotToken() {
        return ""; // <- токен
    }
}
