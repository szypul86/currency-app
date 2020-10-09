package com.szypulski.currencyapp.view;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.model.api.MoneyResponse;
import com.szypulski.currencyapp.service.rest.FixerRestService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consume")
public class FixerRestClient {

  @Value("${api.key}")
  private String API_KEY;

  private final FixerRestService fixerRestService;

  @GetMapping("/latest")
  public ExchangeRateResponse getLatest(@RequestParam String base, @RequestParam String symbols) {
    return fixerRestService.getLatest(base, symbols);
  }

  @GetMapping("/symbols")
  public void saveSymbols() {
    fixerRestService.saveSymbols();
  }


}
