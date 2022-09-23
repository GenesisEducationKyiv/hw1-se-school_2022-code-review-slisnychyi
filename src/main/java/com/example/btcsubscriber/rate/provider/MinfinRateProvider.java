package com.example.btcsubscriber.rate.provider;

import com.jayway.jsonpath.DocumentContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Setter
@Service
public class MinfinRateProvider extends JsonRateProvider {

  private static final String RATE_PATH = "$['data'][0]['price']['uah']";

  @Value("${minfin.api}")
  private String api;

  public MinfinRateProvider(HttpClient httpClient) {
    super(httpClient);
  }

  @Override
  protected String getApi() {
    return api;
  }

  @Override
  protected String getRatePath() {
    return RATE_PATH;
  }

  @Override
  public ProviderType getType() {
    return ProviderType.MINFIN;
  }

  @Override
  protected Long getRateValue(DocumentContext e) {
    return e.read(RATE_PATH, Long.class);
  }

}
