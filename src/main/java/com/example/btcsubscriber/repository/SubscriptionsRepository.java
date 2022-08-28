package com.example.btcsubscriber.repository;

import java.util.List;

public interface SubscriptionsRepository {
    void addSubscription(String email);

    boolean isExist(String email);

    /**
     * Get user subscription emails
     *
     * @return the list of user subscription emails
     */
    List<String> getSubscriptions();
}
