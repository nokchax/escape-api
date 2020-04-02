package com.zum.escape.api.users.endpoint;

import com.zum.escape.api.telegram.distributor.MessageDistributor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.text.MessageFormat;

@Slf4j
@Component
@RequiredArgsConstructor
public class EscapeHelpBot extends TelegramLongPollingBot {
    private final MessageDistributor messageDistributor;
    private final RestTemplate restTemplate;

    @Value("${telegram.bot.name}")
    private String name;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${observing.noticeRoomNo}")
    private String noticeRoomNo;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("{}", update.getMessage().getCaption());
        if (!update.hasMessage()) {
            return;
        }

        File file = downloadFile(update.getMessage());
        log.info("End download file : {}", file != null);

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(messageDistributor.distributeMessage(update.getMessage(), file))
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
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    private File downloadFile(Message message) {
        if(message.getDocument() == null) {
            return null;
        }

        try {
            return downloadFile(getFilePath(message));
        } catch (TelegramApiException e) {
            log.error("Fail to download file : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private String getFilePath(Message message) {
        GetFileResponse response = restTemplate.getForObject(getUrl(message.getDocument()), GetFileResponse.class);

        return response.getResult().getFilePath();
    }

    private String getUrl(Document document) {
        log.info("File id : {}", document.getFileId());
        return MessageFormat.format("https://api.telegram.org/bot{0}/getFile?file_id={1}", this.token, document.getFileId());
    }
}
