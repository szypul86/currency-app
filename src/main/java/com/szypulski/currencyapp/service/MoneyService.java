package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.entity.Money;
import com.szypulski.currencyapp.repository.MoneyRepository;
import com.szypulski.currencyapp.api.MoneyResponse;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {

  private final MoneyRepository moneyRepository;

  public MoneyService(MoneyRepository moneyRepository) {
    this.moneyRepository = moneyRepository;
  }

  public void save(Money money){
    moneyRepository.save(money);
  }

  public Money findBySymbol(String symbol){
    return moneyRepository.findById(symbol).orElseThrow();
  }

  public void saveAllFromResponse(MoneyResponse moneyResponse) {
    moneyRepository.saveAll(moneyResponse.getMoneyMap());
  }
}
