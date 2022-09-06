package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public record EmailService(
        JavaMailSender mailSender,
        SubscriptionsRepository subscriptionsRepository,
        RateService rateService) {


    public void sendEmails() throws RateException {
        Long rate = rateService.getBtcRate()
                .orElseThrow(() -> new RateException("unable to receive rates."));
        String[] emails = subscriptionsRepository.getSubscriptions().toArray(new String[]{});
        SimpleMailMessage msg = generateEmali(rate, emails);
        mailSender.send(msg);
    }

    private SimpleMailMessage generateEmali(Long rate, String[] emails) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emails);
        msg.setSubject("BTC to UAH rate");
        msg.setText("BTC to UAH rate = " + rate);
        return msg;
    }

}
