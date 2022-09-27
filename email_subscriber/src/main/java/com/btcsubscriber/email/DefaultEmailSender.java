package com.btcsubscriber.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class DefaultEmailSender implements EmailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmailSender.class);

  private final JavaMailSender mailSender;

  @Override
  public void send(SimpleMailMessage message) {
    mailSender.send(message);
    LOGGER.info("sent emails for = " + message);
  }

}
