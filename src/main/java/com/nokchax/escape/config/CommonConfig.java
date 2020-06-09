package com.nokchax.escape.config;

import com.nokchax.escape.config.properties.HttpProperty;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class CommonConfig {
    private final AppProperties properties;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(buildCustomConfig());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);

        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory buildCustomConfig() {
        HttpProperty client = properties.getHttpProperty();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(client.getMaxConnTotal())
                .setMaxConnPerRoute(client.getMaxConnPerRoute())
                .build();

        httpRequestFactory.setConnectTimeout(client.getConnectTimeout());
        httpRequestFactory.setReadTimeout(client.getReadTimeout());
        httpRequestFactory.setHttpClient(httpClient);

        return httpRequestFactory;
    }
}
