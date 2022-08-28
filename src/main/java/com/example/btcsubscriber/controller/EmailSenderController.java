package com.example.btcsubscriber.controller;


import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sendEmails")
public class EmailSenderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    private final EmailService emailService;

    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendEmails() {
        LOGGER.info("received request to distribute emails");
        try {
            emailService.sendEmails();
            return ResponseEntity.ok("emails were notified.");
        } catch (RateException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Unable to get rates. Try again later");
        }
    }

}
