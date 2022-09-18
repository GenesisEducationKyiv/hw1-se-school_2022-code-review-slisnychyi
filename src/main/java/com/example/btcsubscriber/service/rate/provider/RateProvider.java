package com.example.btcsubscriber.service.rate.provider;

import java.util.Optional;

public interface RateProvider {

  Optional<Long> getRate();

  ProviderType getType();

}
