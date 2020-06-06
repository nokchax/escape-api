package com.nokchax.escape.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPool {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String prefixName;
}
