package com.example.btcsubscriber.service.email;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import com.example.btcsubscriber.service.rate.RateService;
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
  private final RateService rateService;

  public void sendEmails() throws RateException {
    long rate = rateService.getRate()
        .orElseThrow(() -> new RateException("unable to receive rates."));
    List<String> emails = subscriptionsRepository.getSubscriptions();
    SimpleMailMessage msg = emailGenerator.generateEmail(rate, emails);
    mailSender.send(msg);
  }

}
