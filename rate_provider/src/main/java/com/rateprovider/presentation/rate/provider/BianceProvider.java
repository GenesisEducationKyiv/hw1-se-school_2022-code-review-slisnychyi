package com.rateprovider.presentation.rate.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class BianceProvider extends JsonRateProvider {

  private static final String RATE_PATH = "$['price']";

  @Value("${biance.api}")
  private String api;

  public BianceProvider(HttpClient httpClient) {
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
    return ProviderType.BIANCE;
  }

}
