package com.btcsubscriber.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class StubEmailSender implements EmailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(StubEmailSender.class);

  @Override
  public void send(SimpleMailMessage message) {
    LOGGER.info("sent emails for = " + message);
  }

}
