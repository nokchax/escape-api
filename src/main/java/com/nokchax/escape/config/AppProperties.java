package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.Master;
import com.nokchax.escape.config.properties.Telegram;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
telegram:
    bot:
    name:
    token:

observer:
    admins:

master:
    id:
    pw:
    name:

observing:
    page:
    noticeRoomNo:
*/
// load properties using @ConfigurationProperties annotation
@Data
@Component
@ConfigurationProperties("app")
public class AppProperties {
    private Telegram telegram;
    private Master master;
}
