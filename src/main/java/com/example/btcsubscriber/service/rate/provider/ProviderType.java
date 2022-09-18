package com.example.btcsubscriber.service.rate.provider;

import java.util.Arrays;
import java.util.Optional;

public enum ProviderType {
  MINFIN("minfin"), COIN_BASE("coinbase"), BIANCE("biance");

  private final String name;

  ProviderType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Optional<ProviderType> getType(String name) {
    return Arrays.stream(values())
        .filter(e -> e.getName().equalsIgnoreCase(name))
        .findFirst();
  }

}
