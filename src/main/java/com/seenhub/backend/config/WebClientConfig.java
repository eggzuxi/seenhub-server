package com.seenhub.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("webclient.music")
    private String musicBaseUrl;

    @Value("webclient.thumbnail")
    private String thumbnailBaseUrl;

    @Bean
    public WebClient musicWebClient() {
        return WebClient.builder().baseUrl(musicBaseUrl).build();
    }

    @Bean
    public WebClient thumbnailWebClient() {
        return WebClient.builder().baseUrl(thumbnailBaseUrl).build();
    }

}
