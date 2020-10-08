package com.szypulski.currencyapp.service.rest;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.model.api.MoneyResponse;
import com.szypulski.currencyapp.service.ExchangeRateService;
import com.szypulski.currencyapp.service.MoneyService;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class FixerRestService {
  @Value("${api.key}")
  private String API_KEY /*= "fcafab1d2108e775e67b32cb166581da"*/;

  private final MoneyService moneyService;

  private final ExchangeRateService exchangeRateService;

  private final RestTemplate restTemplate;


  public void saveSymbols() {
    //RestTemplate restTemplate = new RestTemplate();
    String url =
        "http://data.fixer.io/api/symbols?access_key=" + API_KEY;

    ResponseEntity<MoneyResponse> exchange = restTemplate.exchange(url,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        MoneyResponse.class);

    System.out.println(exchange.getBody());
    moneyService.saveAllFromResponse(Objects.requireNonNull(exchange.getBody()));
  }

  public ExchangeRateResponse getLatest(String base, String symbols) {

    //RestTemplate restTemplate = new RestTemplate();
    String url =
        "http://data.fixer.io/api/latest?access_key=" + API_KEY + "&base=" + base + "&symbols="
            + symbols;

    ResponseEntity<ExchangeRateResponse> exchange = restTemplate.exchange(url,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        ExchangeRateResponse.class);

    exchangeRateService.saveExchangeRatesFromSingleExchangeRateResponse(exchange.getBody());
    return exchange.getBody();
  }

}
