package com.example.btcsubscriber;

import com.example.btcsubscriber.subscription.SubscriptionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SubscriptionsRepository subscriptionsRepository;

  @Test
  public void should_subscribeEmail_when_notSubscriberEmailSent() throws Exception {
    //given
    String email = "dummy@email.com";
    //when
    mockMvc.perform(
            MockMvcRequestBuilders
                .post(URI.create("/api/v1/subscription"))
                .content(String.format("{ \"email\" : \"%s\"}", email))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("E-mail was added."));
    //then
    List<String> subscriptions = subscriptionsRepository.getSubscriptions();
    assertThat(subscriptions.size()).isEqualTo(1);
    assertThat(subscriptions.get(0)).isEqualTo(email);

  }

}
