package com.btcsubscriber.presentation;

import com.btcsubscriber.subscription.SubscriptionException;
import com.btcsubscriber.subscription.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscriptionController.class)
class SubscriptionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SubscriptionService subscriptionService;

  @Test
  public void should_subscribeEmails_when_emailNotSubscribed() throws Exception {
    //given
    String email = "dummy@email.com";

    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .post(URI.create("/api/v1/subscription"))
                .content(String.format("{ \"email\" : \"%s\"}", email))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("E-mail was added."));
  }

  @Test
  public void should_notSubscribeEmails_when_emailAlreadySubscribed() throws Exception {
    //given
    String email = "dummy@email.com";
    doThrow(SubscriptionException.class).when(subscriptionService).addSubscription(email);

    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .post(URI.create("/api/v1/subscription"))
                .content(String.format("{ \"email\" : \"%s\"}", email))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

}
