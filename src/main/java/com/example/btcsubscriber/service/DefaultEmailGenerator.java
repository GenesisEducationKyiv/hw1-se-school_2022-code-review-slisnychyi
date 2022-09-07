package com.example.btcsubscriber.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEmailGenerator implements EmailGenerator {

  @Override
  public SimpleMailMessage generateEmail(long rate, List<String> recipients) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(recipients.toArray(new String[] { }));
    msg.setSubject("BTC to UAH rate");
    msg.setText("BTC to UAH rate = " + rate);
    return msg;
  }

}
