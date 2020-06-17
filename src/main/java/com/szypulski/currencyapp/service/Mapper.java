package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.entity.ExchangeRate;
import com.szypulski.currencyapp.api.ExchangeRateResponse;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

  private final MoneyService moneyService;

  public Mapper(MoneyService moneyService) {
    this.moneyService = moneyService;
  }

  public Set<ExchangeRate> mapResponseToEntities(ExchangeRateResponse response){
    Set<ExchangeRate> resultList= new HashSet<>();
    response.getRates().keySet()
    .forEach(symbol -> {
      ExchangeRate exchangeRate = new ExchangeRate();
      exchangeRate.setTimestamp(response.getTimestamp());
      exchangeRate.setFrom(moneyService.findBySymbol(response.getBase()));
      exchangeRate.setTo(moneyService.findBySymbol(symbol));
      exchangeRate.setValue(response.getRates().get(symbol));
      resultList.add(exchangeRate);
    });
    return resultList;
  }
}
