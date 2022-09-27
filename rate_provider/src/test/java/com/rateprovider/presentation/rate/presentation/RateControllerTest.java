package com.rateprovider.presentation.rate.presentation;

import com.rateprovider.presentation.rate.RateService;
import com.rateprovider.presentation.rate.presentation.RateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RateController.class)
class RateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RateService rateService;

  @Test
  public void should_getBtcRate_when_rateExists() throws Exception {
    //given
    when(rateService.getRate()).thenReturn(Optional.of(10L));

    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .get(URI.create("/api/v1/rate"))
        )
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("10"));
  }

  @Test
  public void should_getNotFound_when_noRateExists() throws Exception {
    //given
    when(rateService.getRate()).thenReturn(Optional.empty());

    //when then
    mockMvc.perform(
            MockMvcRequestBuilders
                .get(URI.create("/api/v1/rate"))
        )
        .andExpect(status().isNotFound());
  }

}
