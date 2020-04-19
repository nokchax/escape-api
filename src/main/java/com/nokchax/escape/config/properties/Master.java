package com.nokchax.escape.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "master")
public class Master {
    private String id;
    private String pw;
    private String name;
}
