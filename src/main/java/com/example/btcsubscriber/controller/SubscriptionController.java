package com.example.btcsubscriber.controller;

import com.example.btcsubscriber.exceptions.SubscriptionException;
import com.example.btcsubscriber.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping(value = "/api/v1/subscription", consumes = MediaType.APPLICATION_JSON_VALUE)
public record SubscriptionController(SubscriptionService subscriptionService) {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @PostMapping
    public ResponseEntity<String> subscribeEmail(@RequestBody Subscription subscription) throws SubscriptionException {
        String email = subscription.email();
        LOGGER.info("receive request to subscribe. [email={}]", email);
        subscriptionService.addSubscription(email);
        return ResponseEntity.ok("E-mail was added.");
    }
}

record Subscription(
        @Email(message = "Email is not valid")
        @NotEmpty(message = "Email cannot be empty") String email) {
}
