package com.nokchax.escape.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "httpclient")
public class HttpClient {
    private int maxConnTotal;
    private int maxConnPerRoute;
    private int connectTimeout;//mill
    private int readTimeout;//mill
}
