package com.btcsubscriber.service;

import com.btcsubscriber.client.RateClient;
import com.btcsubscriber.email.EmailException;
import com.btcsubscriber.email.EmailGenerator;
import com.btcsubscriber.email.EmailSender;
import com.btcsubscriber.email.EmailService;
import com.btcsubscriber.subscription.SubscriptionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock
  private EmailSender mailSender;
  @Mock
  private RateClient rateClient;
  @Mock
  private SubscriptionsRepository subscriptionsRepository;
  @Mock
  private EmailGenerator emailGenerator;

  @Test
  public void should__when_() throws EmailException {
    //given
    List<String> emails = List.of("dummy@email.com");
    SimpleMailMessage message = new SimpleMailMessage();
    EmailService emailService = new EmailService(mailSender, emailGenerator, subscriptionsRepository, rateClient);
    when(rateClient.getRate()).thenReturn(Optional.of(10L));
    when(subscriptionsRepository.getSubscriptions()).thenReturn(emails);
    when(emailGenerator.generateEmail(10, emails)).thenReturn(message);
    //when
    emailService.sendEmails();
    //then
    verify(mailSender, times(1)).send(message);
  }

  @Test
  public void should_throwException_when_cantGetRates() {
    //given
    EmailService emailService = new EmailService(mailSender, emailGenerator, subscriptionsRepository, rateClient);
    given(rateClient.getRate()).willAnswer(invocationOnMock -> {
      throw new EmailException("");
    });
    // when then
    assertThatThrownBy(emailService::sendEmails).isInstanceOf(EmailException.class);
  }

}
