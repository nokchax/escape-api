package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.Admin;
import com.nokchax.escape.config.properties.Master;
import com.nokchax.escape.config.properties.Selenium;
import com.nokchax.escape.config.properties.TelegramBot;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
    app:
      telegramBot:
        name:
        token:

      admin:
        ids:

      master:
        id:
        pw:
        name:
      selenium:
        path:
*/
// load properties using @ConfigurationProperties annotation
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private TelegramBot telegram;
    private Master master;
    private Admin admin;
    private Selenium selenium;
}
