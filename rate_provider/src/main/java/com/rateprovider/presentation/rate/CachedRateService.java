package com.rateprovider.presentation.rate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

public class CachedRateService implements RateService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CachedRateService.class);
  private static final int DEFAULT_TIMEOUT_MINUTES = 5;

  private final RateService rateService;

  private LocalDateTime lastUpdate;
  private long cachedValue;

  public CachedRateService(RateService rateService) {
    this.rateService = rateService;
  }

  @Override
  public Optional<Long> getRate() {
    LocalDateTime now = LocalDateTime.now();
    if (lastUpdate != null && lastUpdate.isAfter(now)) {
      LOGGER.info("retrieved cached rate = " + cachedValue);
      return Optional.of(cachedValue);
    }
    return rateService.getRate()
        .map(rate -> {
          lastUpdate = now.plusMinutes(DEFAULT_TIMEOUT_MINUTES);
          cachedValue = rate;
          return rate;
        });
  }

}
