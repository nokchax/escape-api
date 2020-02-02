package com.zum.escape.api.observing;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Slf4j
@Service
@NoArgsConstructor
public class ObservingService {
    @Value("${observing.page}")
    private String page;
    @Value("${observing.noticeRoomNo}")
    private Long noticeRoomNo;

    private String previousStatus = "";

    public boolean scanPage() {
        String currentStatus = getStatus();

        if(previousStatus.equalsIgnoreCase(currentStatus)) {
            return false;
        }

        previousStatus = currentStatus;
        return true;
    }

    private String getStatus() {
        return "";
    }

    public void updateNoticeTargetRoom(Long chatId) {
        this.noticeRoomNo = chatId;
    }

    public SendMessage createNotice() {
        return new SendMessage()
                .setChatId(this.noticeRoomNo)
                .setText(this.previousStatus);
    }

    public String getCurrentStatus() {
        return this.previousStatus;
    }
}
