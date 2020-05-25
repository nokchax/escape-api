package com.nokchax.escape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@EnableAsync
@Configuration
public class Async {
    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

        // TODO: 2020-05-25 configurable
        threadPoolExecutor.setCorePoolSize(20);
        threadPoolExecutor.setMaxPoolSize(40);
        threadPoolExecutor.setQueueCapacity(30);
        threadPoolExecutor.setThreadNamePrefix("THREAD-POOL-");
        threadPoolExecutor.initialize();

        return threadPoolExecutor;
    }
}
