package com.szypulski.currencyapp.view;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.model.api.MoneyResponse;
import com.szypulski.currencyapp.service.ExchangeRateService;
import com.szypulski.currencyapp.service.MoneyService;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consume")
public class FixerRestClient {

  private final String API_KEY = "fcafab1d2108e775e67b32cb166581da";

  private final MoneyService moneyService;

  private final ExchangeRateService exchangeRateService;

  public FixerRestClient(MoneyService moneyService,
      ExchangeRateService exchangeRateService) {
    this.moneyService = moneyService;
    this.exchangeRateService = exchangeRateService;
  }

  @GetMapping("/latest")
  public ExchangeRateResponse getLatest(@RequestParam String base, @RequestParam String symbols) {

    RestTemplate rest = new RestTemplate();
    String url =
        "http://data.fixer.io/api/latest?access_key=" + API_KEY + "&base=" + base + "&symbols="
            + symbols;

    ResponseEntity<ExchangeRateResponse> exchange = rest.exchange(url,
        HttpMethod.GET,
        HttpEntity.EMPTY,
        ExchangeRateResponse.class);

    System.out.println(exchange.getBody());
    exchangeRateService.saveExchangeRatesFromSingleExchangeRateResponse(exchange.getBody());
    return exchange.getBody();
  }

  @PostConstruct
  @GetMapping("/symbols")
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


}
