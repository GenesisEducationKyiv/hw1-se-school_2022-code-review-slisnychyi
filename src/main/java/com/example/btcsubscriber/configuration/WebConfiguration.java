package com.example.btcsubscriber.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class WebConfiguration {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().build();
    }

}
