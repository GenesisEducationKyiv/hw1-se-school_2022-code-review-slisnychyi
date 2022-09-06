package com.example.btcsubscriber.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemmorySubscriptionRepository implements SubscriptionsRepository {
    private final List<String> emails;

    public InMemmorySubscriptionRepository() {
        this.emails = new ArrayList<>();
    }

    @Override
    public void addSubscription(String email) {
        emails.add(email);
    }

    @Override
    public boolean isExist(String email) {
        return emails.stream().anyMatch(e -> e.equals(email));
    }

    @Override
    public List<String> getSubscriptions() {
        return emails;
    }
}
