package com.rateprovider.presentation.rate.provider;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class JsonRateProvider implements RateProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonRateProvider.class);

  private final HttpClient httpClient;

  @Override
  public Optional<Long> getRate() {
    HttpRequest request = HttpRequest.newBuilder(URI.create(getApi())).build();
    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      Optional<Long> rate = Optional.of(response)
          .map(HttpResponse::body)
          .map(JsonPath::parse)
          .map(this::getRateValue);
      LOGGER.info("provider = [{}], rate = [{}] ", getType(), rate.map(Object::toString).orElse(""));
      return rate;
    }
    catch (IOException | InterruptedException e) {
      LOGGER.error("unable to get btc rate.", e);
      return Optional.empty();
    }
  }

  protected Long getRateValue(DocumentContext e) {
    String value = e.read(getRatePath(), String.class);
    return Long.valueOf(value.substring(0, value.indexOf('.')));
  }

  protected abstract String getApi();

  protected abstract String getRatePath();

}
