package com.example.btcsubscriber.controller;

import com.example.btcsubscriber.exceptions.RateException;
import com.example.btcsubscriber.service.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmailSenderController.class)
class EmailSenderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmailService emailService;

  @Test
  public void should_distributeEmails_when_requestArrived() throws Exception {
    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .post(URI.create("/api/v1/sendEmails"))
                )
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("emails were distributed."));

    verify(emailService, times(1)).sendEmails();
  }

  @Test
  public void should_getServiceNotAvaliable_when_notPosibleToDistributeEmails() throws Exception {
    doThrow(RateException.class).when(emailService).sendEmails();
    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .post(URI.create("/api/v1/sendEmails"))
                )
        .andExpect(status().isServiceUnavailable())
        .andExpect(MockMvcResultMatchers.content().string("Unable to get rates. Try again later"));
  }



}
