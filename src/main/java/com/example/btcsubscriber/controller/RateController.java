package com.example.btcsubscriber.controller;

import com.example.btcsubscriber.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate")
public record RateController(RateService rateService) {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);

    @GetMapping
    public ResponseEntity<Long> getBtcRatePrice() {
        return rateService.getBtcRate()
                .map(rate -> {
                    LOGGER.info("get btc to uah rate. [rate = {}]", rate);
                    return ResponseEntity.ok(rate);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
