package com.btcsubscriber.email;

import com.btcsubscriber.client.RateClient;
import com.btcsubscriber.subscription.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final EmailSender emailSender;
  private final EmailGenerator emailGenerator;
  private final SubscriptionsRepository subscriptionsRepository;
  private final RateClient rateCLient;

  public void sendEmails() throws EmailException {
    long rate = rateCLient.getRate()
        .orElseThrow(() -> new EmailException("unable to receive rates."));
    List<String> emails = subscriptionsRepository.getSubscriptions();
    SimpleMailMessage msg = emailGenerator.generateEmail(rate, emails);
    emailSender.send(msg);
  }

}
