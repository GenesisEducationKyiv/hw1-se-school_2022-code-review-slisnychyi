package com.example.btcsubscriber.service.rate.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class CoinbaseProvider extends JsonRateProvider{

  private static final String RATE_PATH = "$['data']['amount']";

  @Value("${coinbase.api}")
  private String api;

  public CoinbaseProvider(HttpClient httpClient) {
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
    return ProviderType.COIN_BASE;
  }

}
