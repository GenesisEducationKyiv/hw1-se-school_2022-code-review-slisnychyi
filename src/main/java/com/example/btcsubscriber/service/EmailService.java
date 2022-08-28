package com.example.btcsubscriber.service;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.repository.SubscriptionsRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SubscriptionsRepository subscriptionsRepository;
    private final RateService rateService;

    public EmailService(SubscriptionsRepository subscriptionsRepository, JavaMailSender javaMailSender, RateService rateService) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.javaMailSender = javaMailSender;
        this.rateService = rateService;
    }

    public void sendEmails() throws RateException {
        Long rate = rateService.getBtcRate()
                .orElseThrow(() -> new RateException("unable to receive rates."));
        String[] emails = subscriptionsRepository.getSubscriptions().toArray(new String[]{});
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emails);
        msg.setSubject("BTC to UAH rate");
        msg.setText("BTC to UAH rate = " + rate);
        javaMailSender.send(msg);
    }

}
