package com.btcsubscriber.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@FeignClient(value = "rateService", url = "localhost:8082")
public interface RateClient {

  @GetMapping(value = "/api/v1/rate")
  Optional<Long> getRate();

}
