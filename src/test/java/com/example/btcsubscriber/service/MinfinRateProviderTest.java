package com.example.btcsubscriber.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinfinRateProviderTest {

  @Test
  @DisplayName("""
          given external rate service that returns crypto rates
          when call rate service
          should return the current rate for given currency
      """)
  public void should_getBtcRate_when_serverIsWorking() throws IOException, InterruptedException, URISyntaxException {
    //given
    String btcApi = "https://minfin.com.ua/api/currency/crypto/list/?filter[code]=btc";
    HttpClient httpClient = mock(HttpClient.class);
    HttpResponse response = mock(HttpResponse.class);
    MinfinRateProvider service = new MinfinRateProvider(httpClient);
    service.setBtcApi(btcApi);
    URI uri = Objects.requireNonNull(MinfinRateProviderTest.class.getResource("/testBtcResponse.json")).toURI();
    String body = String.join(
        "",
        Files.readAllLines(Path.of(uri))
    );
    HttpRequest request = HttpRequest.newBuilder(URI.create(btcApi)).build();
    when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
    when(response.body()).thenReturn(body);
    //when
    Long result = service.getRate().orElse(0L);
    //then
    Assertions.assertThat(result).isEqualTo(907196);
  }

}
