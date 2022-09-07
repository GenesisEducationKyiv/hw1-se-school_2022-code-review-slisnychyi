package com.example.btcsubscriber.service;

import java.util.Optional;

public interface RateProvider {

  Optional<Long> getRate();

}
