package com.example.btcsubscriber.service;

import com.example.btcsubscriber.controller.RateController;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class RateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);

    private final String btcApi;
    private final HttpClient httpClient;

    public RateService(@Value("${btc.api}") String btcApi, HttpClient httpClient) {
        this.btcApi = btcApi;
        this.httpClient = httpClient;
    }

    public Optional<Long> getBtcRate() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(btcApi)).build();
        try {
            return Optional.of(httpClient.send(request, HttpResponse.BodyHandlers.ofString()))
                    .map(HttpResponse::body)
                    .map(JsonPath::parse)
                    .map(e -> e.read("$['data'][0]['price']['uah']", Long.class));
        } catch (IOException | InterruptedException e) {
            LOGGER.error("unable to get btc rate.", e);
            return Optional.empty();
        }
    }
}
