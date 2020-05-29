package com.nokchax.escape.config;

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
public class CommonConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(buildCustomConfig());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);

        return restTemplate;
    }

    // TODO: 2020-05-25 configurable
    private HttpComponentsClientHttpRequestFactory buildCustomConfig() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .build();

        httpRequestFactory.setConnectTimeout(2000);
        httpRequestFactory.setReadTimeout(5000);
        httpRequestFactory.setHttpClient(httpClient);

        return httpRequestFactory;
    }
}
