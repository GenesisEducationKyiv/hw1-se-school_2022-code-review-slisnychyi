package com.example.btcsubscriber.subscription;

import java.util.List;

public interface SubscriptionsRepository {
    void addSubscription(String email);

    boolean isExist(String email);

    List<String> getSubscriptions();
}
