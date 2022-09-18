package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import com.example.btcsubscriber.service.email.EmailGenerator;
import com.example.btcsubscriber.service.email.EmailService;
import com.example.btcsubscriber.service.rate.RateService;
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
  private JavaMailSender mailSender;
  @Mock
  private RateService rateService;
  @Mock
  private SubscriptionsRepository subscriptionsRepository;
  @Mock
  private EmailGenerator emailGenerator;

  @Test
  public void should__when_() throws RateException {
    //given
    List<String> emails = List.of("dummy@email.com");
    SimpleMailMessage message = new SimpleMailMessage();
    EmailService emailService = new EmailService(mailSender, emailGenerator, subscriptionsRepository, rateService);
    when(rateService.getRate()).thenReturn(Optional.of(10L));
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
    EmailService emailService = new EmailService(mailSender, emailGenerator, subscriptionsRepository, rateService);
    given(rateService.getRate()).willAnswer(invocationOnMock -> {
      throw new RateException("");
    });
    // when then
    assertThatThrownBy(emailService::sendEmails).isInstanceOf(RateException.class);
  }

}
