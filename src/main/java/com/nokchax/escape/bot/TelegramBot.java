package com.nokchax.escape.bot;

import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.message.distributor.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final MessageHandler messageHandler;
    private final AppProperties properties;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(messageHandler.handle(update.getMessage()))
                .setParseMode(ParseMode.MARKDOWN);

        sendMessage(message);
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Fail to send response message : {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getTelegram()
                .getName();
    }

    @Override
    public String getBotToken() {
        return properties.getTelegram()
                .getToken();
    }
}
