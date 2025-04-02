package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Random;


public class TelegramBot extends TelegramLongPollingBot {

    private HashMap<Long, Integer> statistic;

    public TelegramBot() {
        this.statistic = (HashMap<Long, Integer>) Database.load();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String playerMessage = update.getMessage().getText().toLowerCase();
            Long chatId = update.getMessage().getChatId();

            if (playerMessage.equals("камень") || playerMessage.equals("ножницы") || playerMessage.equals("бумага")) {
                String[] options = {"Камень", "Бумага", "Ножницы"};
                Random random = new Random();
                int number = random.nextInt(3);

                String botMessage = options[number].toLowerCase();
                sendMessage(update, botMessage);

                if (playerMessage.equals(botMessage)) {
                    sendMessage(update, "Ничья!");
                } else if (
                                (playerMessage.equals("камень") && botMessage.equals("ножницы")) ||
                                (playerMessage.equals("бумага") && botMessage.equals("камень")) ||
                                (playerMessage.equals("ножницы") && botMessage.equals("бумага"))
                ) {
                    sendMessage(update, "Ты победил! 🎉");
                    addWin(chatId);
                } else {
                    sendMessage(update, "Ты проиграл! 😢");
                }
            }
            else if (playerMessage.equals("/wins")) {
                sendMessage(update, "Количество побед: " + getWins(chatId));
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

    public void addWin(Long chatId) {
        statistic.put(chatId, statistic.getOrDefault(chatId, 0) + 1);
        Database.save(statistic);
    }

    public int getWins(Long chatId) {
        return statistic.getOrDefault(chatId, 0);
    }

    @Override
    public String getBotUsername() {
        return "RPSBot";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}
