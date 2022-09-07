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
  private final EmailGenerator emailGenerator;
  private final SubscriptionsRepository subscriptionsRepository;
  private final RateProvider rateProvider;

  public void sendEmails() throws RateException {
    long rate = rateProvider.getRate()
        .orElseThrow(() -> new RateException("unable to receive rates."));
    List<String> emails = subscriptionsRepository.getSubscriptions();
    SimpleMailMessage msg = emailGenerator.generateEmail(rate, emails);
    mailSender.send(msg);
  }

}
