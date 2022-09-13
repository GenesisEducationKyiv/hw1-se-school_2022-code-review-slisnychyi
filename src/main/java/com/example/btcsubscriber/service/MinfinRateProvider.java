package com.example.btcsubscriber.service;

import com.example.btcsubscriber.controller.RateController;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@Setter
@RequiredArgsConstructor
public class MinfinRateProvider implements RateProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);
  private static final String RATE_PATH = "$['data'][0]['price']['uah']";

  private final HttpClient httpClient;
  @Value("${btc.api}")
  private String btcApi;

  @Override
  public Optional<Long> getRate() {
    HttpRequest request = HttpRequest.newBuilder(URI.create(btcApi)).build();
    try {
      return Optional.of(httpClient.send(request, HttpResponse.BodyHandlers.ofString()))
          .map(HttpResponse::body)
          .map(JsonPath::parse)
          .map(MinfinRateProvider::getRateValue);
    }
    catch (IOException | InterruptedException e) {
      LOGGER.error("unable to get btc rate.", e);
      return Optional.empty();
    }
  }

  private static Long getRateValue(DocumentContext e) {
    return e.read(RATE_PATH, Long.class);
  }

}
