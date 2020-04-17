package com.nokchax.escape.leetcode.crawl.api.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokchax.escape.config.AppProperties;
import com.nokchax.escape.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeleniumPool {
    private final AppProperties properties;
    private final ObjectMapper objectMapper;
    private final UserAgentQueue userAgentQueue;
    private final Map<String, SeleniumBrowser> seleniumPool = new HashMap<>(); // key : user's id

    public SeleniumBrowser getSeleniumBrowser(User user) {
        if (!seleniumPool.containsKey(user.getId())) {
            log.info("Open browser for " + user.getId());
            seleniumPool.put(user.getId(), new SeleniumBrowser(user, userAgentQueue, objectMapper));
        }

        return seleniumPool.get(user.getId());
    }

    public void removeSeleniumBrowser(User user) {
        log.info("Close browser for " + user.getId());
        seleniumPool.get(user.getId())
                .close();

        seleniumPool.remove(user.getId());
    }

    @PostConstruct
    private void init() {
        System.setProperty("webdriver.chrome.driver", properties.getSelenium().getDriverPath());
    }
}
