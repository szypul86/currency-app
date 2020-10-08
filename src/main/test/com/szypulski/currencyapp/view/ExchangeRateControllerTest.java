package com.szypulski.currencyapp.view;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szypulski.currencyapp.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ExchangeRateControllerTest extends TestBase {


  @Test
  @WithMockUser(roles = "USER")
  public void whenGetLatestExchangeRateWithDefaultFromReturnsOk() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to = "USD";
    Double value = 1.1712;

    saveExchangeRate(from, to, value, 0);

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/" + from + "/" + to)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.value").value(value))
        .andExpect(jsonPath("$.from").value(from))
        .andExpect(jsonPath("$.to").value(to));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetLatestExchangeRateWithCustomFromReturnsOk() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to1 = "USD";
    String to2 = "PLN";
    Double value1 = 1.5;
    Double value2 = 3.0;
    double result = value2 / value1;

    saveExchangeRate(from, to1, value1, 0);
    saveExchangeRate(from, to2, value2, 0);

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/" + to1 + "/" + to2)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.value").value(result))
        .andExpect(jsonPath("$.from").value(to1))
        .andExpect(jsonPath("$.to").value(to2));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetLatestExchangeRateWithCustomFromAndDefaultFromReturnsOk() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to = "USD";
    double value = 1.5;
    double result = 1 / value;

    saveExchangeRate(from, to, value, 0);

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/" + to + "/" + BASE_CURRENCY)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.value").value(result))
        .andExpect(jsonPath("$.from").value(to))
        .andExpect(jsonPath("$.to").value(BASE_CURRENCY));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetLatestExchangeRateWithNotExistingSymbolReturnsNotFound() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to = "USD";
    String notTo = "HUN";

    saveExchangeRate(from, to, 1.1712, 0);

    ResultActions perform =
        mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/" + from + "/" + notTo));

    perform.andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetHistoricalExchangeRatesWithCustomFromReturnsOk() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to1 = "USD";
    String to2 = "PLN";
    double value1 = 1.5;
    double value2 = 3.0;
    int j = 30;

    for (int i = 0; i <= j; i++) {
      saveExchangeRate(from, to1, value1 + 0.001 * i, i);
      saveExchangeRate(from, to2, value2 + 0.001 * i, i);
    }
    int pageNumber = 0;
    int pageSize = 10;

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/historical/" + from + "/" + to1)
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(10)))
        .andExpect(jsonPath("$.[0].value").value(value1 + 0.001 * (j - j)))
        .andExpect(jsonPath("$.[9].value").value(value1 + 0.001 * (j - 9)));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetHistoricalExchangeRatesWithCustomFromWithCustomPageReturnsOk()
      throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to1 = "USD";
    String to2 = "PLN";
    double value1 = 1.5;
    double value2 = 3.0;
    int j = 30;

    for (int i = 0; i <= j; i++) {
      saveExchangeRate(from, to1, value1 + 0.001 * i, i);
      saveExchangeRate(from, to2, value2 + 0.001 * i, i);
    }
    int pageNumber = 1;
    int pageSize = 20;

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/historical/" + from + "/" + to1)
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(11)))
        .andExpect(jsonPath("$.[0].value").value(value1 + 0.001 * (j - 20)))
        .andExpect(jsonPath("$.[9].value").value(value1 + 0.001 * (j - 29)));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetHistoricalExchangeRatesWithCustomFromWithNotExistingPageReturnsOKAndLastSetOfResults()
      throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to1 = "USD";
    String to2 = "PLN";
    double value1 = 1.5;
    double value2 = 3.0;
    int j = 30;

    for (int i = 0; i <= j; i++) {
      saveExchangeRate(from, to1, value1 + 0.001 * i, i);
      saveExchangeRate(from, to2, value2 + 0.001 * i, i);
    }
    int pageNumber = 3;
    int pageSize = 20;

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/historical/" + from + "/" + to1)
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(11)))
        .andExpect(jsonPath("$.[0].value").value(value1 + 0.001 * (j - 20)))
        .andExpect(jsonPath("$.[9].value").value(value1 + 0.001 * (j - 29)));
  }

  @Test
  @WithMockUser(roles = "USER")
  public void whenGetHistoricalExchangeRatesWithDefaultFromReturnsOk() throws Exception {
    saveDefaultMoneys();
    String from = BASE_CURRENCY;
    String to1 = "USD";
    String to2 = "PLN";
    double value1 = 1.5;
    double value2 = 3.0;
    int j = 30;

    for (int i = 0; i <= j; i++) {
      saveExchangeRate(from, to1, value1 + 0.1 * i, i);
      saveExchangeRate(from, to2, value2 + 0.1 * i, i);
    }
    int pageNumber = 0;
    int pageSize = 10;

    mockMvc.perform(MockMvcRequestBuilders.get("/exchange-rates/historical/" + to2 + "/" + to1)
        .param("page", String.valueOf(pageNumber))
        .param("size", String.valueOf(pageSize))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(10)))
        .andExpect(jsonPath("$.[0].value").value((value1 + (0.1 * (0))) / (value2 + 0.1 * (0))))
        .andExpect(jsonPath("$.[9].value").value((value1 + 0.1 * (9)) / (value2 + 0.1 * (9))));
  }
}