package com.example.btcsubscriber.service.rate;

import com.example.btcsubscriber.service.rate.provider.RateProvider;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultRateService implements RateService {

  private final List<RateProvider> rateProviders;

  @Override
  public Optional<Long> getRate() {
    return rateProviders.stream()
        .map(RateProvider::getRate)
        .filter(Optional::isPresent)
        .findFirst()
        .orElseGet(Optional::empty);
  }

}
