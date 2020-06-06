package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.ThreadPool;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@EnableAsync
@Configuration
@RequiredArgsConstructor
public class Async {
    private final AppProperties properties;

    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        ThreadPool config = properties.getThreadPool();
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

        threadPoolExecutor.setCorePoolSize(config.getCorePoolSize());
        threadPoolExecutor.setMaxPoolSize(config.getMaxPoolSize());
        threadPoolExecutor.setQueueCapacity(config.getQueueCapacity());
        threadPoolExecutor.setThreadNamePrefix(config.getPrefixName());
        threadPoolExecutor.initialize();

        return threadPoolExecutor;
    }
}
