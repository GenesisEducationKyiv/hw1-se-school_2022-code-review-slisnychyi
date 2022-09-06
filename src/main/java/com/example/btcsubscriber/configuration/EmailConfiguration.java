package com.example.btcsubscriber.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

  @Bean
  public JavaMailSenderImpl mailSender(MailCredentials credentials) {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setProtocol(credentials.protocol());
    javaMailSender.setHost(credentials.host());
    javaMailSender.setPort(credentials.port());
    return javaMailSender;
  }

  @ConstructorBinding
  @ConfigurationProperties(prefix = "mail")
  record MailCredentials(String protocol, String host, int port) {}

}
