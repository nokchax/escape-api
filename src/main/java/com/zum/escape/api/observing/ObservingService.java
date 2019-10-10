package com.zum.escape.api.observing;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@NoArgsConstructor
public class ObservingService {
    @Value("${observing.page}")
    private String page;

    private String previousStatus = "";

    public boolean scanPage() {
        String currentStatus = getStatus();

        if(previousStatus.equalsIgnoreCase(currentStatus))
            return false;

        previousStatus = currentStatus;
        return true;
    }

    public String getCurrentStatus() {
        return this.previousStatus;
    }

    private String getStatus() {
        Connection connect = Jsoup.connect("https://edu.nextstep.camp/c/8fWRxNWU/");
        Document document = null;
        try {
            document = connect.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element body = document.body();
        int startIdx = body.toString().indexOf("\"sessions\":[{");
        String temp = body.toString().substring(startIdx);
        int endIdx = temp.indexOf(']');
        String sessions = temp.substring(0, endIdx);
        int statusIdx = sessions.indexOf("\"status\":");
        String statusTempt = sessions.substring(statusIdx);
        int statusEndIdx = statusTempt.indexOf(',');
        String status = statusTempt.substring(0, statusEndIdx);

        return status;
    }
}
