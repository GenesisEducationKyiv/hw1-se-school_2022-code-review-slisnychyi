package com.example.btcsubscriber.service.email;

import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmailGenerator {

  SimpleMailMessage generateEmail(long rate, List<String> recipients);

}
