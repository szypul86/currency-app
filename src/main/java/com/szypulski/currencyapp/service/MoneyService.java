package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.api.MoneyResponse;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.repository.MoneyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {

  private final MoneyRepository moneyRepository;

  public MoneyService(MoneyRepository moneyRepository) {
    this.moneyRepository = moneyRepository;
  }

  public void save(Money money) {
    if (moneyRepository.findById(money.getSymbol()).isEmpty()) {
      moneyRepository.save(money);
    }
  }

  public List<Money> findBySymbolIn(List<String> symbols) {
    return moneyRepository.findBySymbolIn(symbols);
  }

  public Money findBySymbol(String symbol) {
    return moneyRepository.findById(symbol).orElseThrow();
  }

  public void saveAllFromResponse(MoneyResponse moneyResponse) {
    moneyRepository.saveAll(moneyResponse.getMoneyMap());
  }
}
