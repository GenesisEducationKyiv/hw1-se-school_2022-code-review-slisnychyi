package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceTest {

  @Test
  public void should__when_() throws RateException {
    //given
    List<String> emails = List.of("dummy@email.com");
    JavaMailSender mailSender = mock(JavaMailSender.class);
    RateProvider rateProvider = mock(RateProvider.class);
    SubscriptionsRepository subscriptionsRepository = mock(SubscriptionsRepository.class);
    EmailService emailService = new EmailService(mailSender, subscriptionsRepository, rateProvider);
    when(rateProvider.getRate()).thenReturn(Optional.of(10L));
    when(subscriptionsRepository.getSubscriptions()).thenReturn(emails);
    //when
    emailService.sendEmails();
    //then
    SimpleMailMessage mailMessage = emailService.generateEmail(10L, emails);
    verify(mailSender, times(1)).send(mailMessage);
  }

  @Test
  public void should_throwException_when_cantGetRates() {
    //given
    JavaMailSender mailSender = mock(JavaMailSender.class);
    RateProvider rateProvider = mock(RateProvider.class);
    SubscriptionsRepository subscriptionsRepository = mock(SubscriptionsRepository.class);
    EmailService emailService = new EmailService(mailSender, subscriptionsRepository, rateProvider);
    given(rateProvider.getRate()).willAnswer(invocationOnMock -> {
      throw new RateException("");
    });
    // when then
    assertThatThrownBy(emailService::sendEmails).isInstanceOf(RateException.class);
  }

}
