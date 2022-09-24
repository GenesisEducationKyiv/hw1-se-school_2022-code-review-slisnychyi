package com.example.btcsubscriber.email;

import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmailGenerator {

  SimpleMailMessage generateEmail(long rate, List<String> recipients);

}
