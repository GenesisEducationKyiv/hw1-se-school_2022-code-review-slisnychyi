package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.SubscriptionException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public record SubscriptionService(SubscriptionsRepository subscriptionsRepository) {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    public void addSubscription(String email) throws SubscriptionException {
        if (subscriptionsRepository.isExist(email)) {
            String message = String.format("Subscription with email[%s] already exists.", email);
            LOGGER.error(message);
            throw new SubscriptionException(message);
        }
        subscriptionsRepository.addSubscription(email);
        LOGGER.info("Subscription for email[{}] was added.", email);
    }

}
