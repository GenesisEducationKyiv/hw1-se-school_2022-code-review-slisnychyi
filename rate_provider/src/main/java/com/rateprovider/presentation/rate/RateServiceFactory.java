package com.rateprovider.presentation.rate;

import com.rateprovider.presentation.rate.provider.RateProvider;

import java.util.List;

public class RateServiceFactory {

  public static RateService build(boolean cached, List<RateProvider> providers) {
    DefaultRateService defaultRateServiceChain = new DefaultRateService(providers);
    return cached ? new CachedRateService(defaultRateServiceChain) : defaultRateServiceChain;
  }

}
