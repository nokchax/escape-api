package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.Master;
import com.nokchax.escape.config.properties.Telegram;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

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
@Configuration("app")
public class AppProperties {
    private Telegram telegram;
    private Master master;
}
