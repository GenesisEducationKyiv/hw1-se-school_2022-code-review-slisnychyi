package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final SubscriptionsRepository subscriptionsRepository;
  private final RateProvider rateProvider;

  public void sendEmails() throws RateException {
    Long rate = rateProvider.getRate()
        .orElseThrow(() -> new RateException("unable to receive rates."));
    List<String> emails = subscriptionsRepository.getSubscriptions();
    SimpleMailMessage msg = generateEmail(rate, emails);
    mailSender.send(msg);
  }

  SimpleMailMessage generateEmail(Long rate, List<String> emails) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(emails.toArray(new String[] { }));
    msg.setSubject("BTC to UAH rate");
    msg.setText("BTC to UAH rate = " + rate);
    return msg;
  }

}
