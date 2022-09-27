package com.btcsubscriber.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSender {

  void send(SimpleMailMessage message);

}
