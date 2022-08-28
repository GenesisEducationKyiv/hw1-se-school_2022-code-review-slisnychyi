package com.example.btcsubscriber.controller;

import com.example.btcsubscriber.repository.SubscriptionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(value = "/api/subscription")
public class SubscriptionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionsRepository subscriptionsRepository;

    public SubscriptionController(SubscriptionsRepository subscriptionsRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
    }

    @PostMapping
    public ResponseEntity<String> subscribeEmail(@RequestBody Subscription subscription) {
        String email = subscription.email();
        LOGGER.info("receive request to subscribe. [email={}]", email);
        if (subscriptionsRepository.isExist(email)) {
            return ResponseEntity.status(409).body("Email already exist.");
        } else {
            subscriptionsRepository.addSubscription(email);
            return ResponseEntity.ok("E-mail was added.");
        }
    }
}

record Subscription(
        @Email(message = "Email is not valid")
        @NotEmpty(message = "Email cannot be empty") String email) {
}
