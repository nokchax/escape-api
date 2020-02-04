package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.Master;
import com.nokchax.escape.config.properties.Telegram;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
app:
  telegram:
    name:
    token:

  admin:
    ids:

  master:
    id:
    pw:
    name:
*/
// load properties using @ConfigurationProperties annotation
@Data
@Component
@ConfigurationProperties("app")
public class AppProperties {
    private Telegram telegram;
    private Master master;
}
