package com.szypulski.currencyapp.service.rest;

import com.szypulski.currencyapp.api.ExchangeRateResponse;
import com.szypulski.currencyapp.api.MoneyResponse;
import com.szypulski.currencyapp.service.ExchangeRateService;
import com.szypulski.currencyapp.service.MoneyService;
import java.util.Objects;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FixerRestService {

  private final String API_KEY = "fcafab1d2108e775e67b32cb166581da";

  private final MoneyService moneyService;

  private final ExchangeRateService exchangeRateService;

  public FixerRestService(MoneyService moneyService,
      ExchangeRateService exchangeRateService) {
    this.moneyService = moneyService;
    this.exchangeRateService = exchangeRateService;
  }

  public void saveSymbols() {
    RestTemplate rest = new RestTemplate();
    String url =
        "http://data.fixer.io/api/symbols?access_key=" + API_KEY;

    ResponseEntity<MoneyResponse> exchange = rest.exchange(url,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        MoneyResponse.class);

    System.out.println(exchange.getBody());
    moneyService.saveAllFromResponse(Objects.requireNonNull(exchange.getBody()));
  }

  public ExchangeRateResponse getLatest(String base, String symbols) {

    RestTemplate rest = new RestTemplate();
    String url =
        "http://data.fixer.io/api/latest?access_key=" + API_KEY + "&base=" + base + "&symbols="
            + symbols;

    ResponseEntity<ExchangeRateResponse> exchange = rest.exchange(url,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        ExchangeRateResponse.class);

    exchangeRateService.saveExchangeRatesFromSingleExchangeRateResponse(exchange.getBody());
    return exchange.getBody();
  }

}
