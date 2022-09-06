package com.example.btcsubscriber.controller;

import com.example.btcsubscriber.exceptions.SubscriptionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<String> handleCityNotFoundException(SubscriptionException exception) {
        return ResponseEntity.status(409).body(exception.getMessage());
    }

}