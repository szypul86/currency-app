package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.api.MoneyResponse;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.repository.MoneyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoneyService {

  private final MoneyRepository moneyRepository;


  public void save(Money money) {
    if (moneyRepository.findById(money.getSymbol()).isEmpty()) {
      moneyRepository.save(money);
    }
  }

  public List<Money> findBySymbolIn(List<String> symbols) {
    return moneyRepository.findBySymbolIn(symbols);
  }

  public Money findBySymbol(String symbol) {
    return moneyRepository.findById(symbol).orElseThrow(
        ()-> new NoSuchElementException("Symbol: " + symbol + " does not exist"));
  }

  public void saveAllFromResponse(MoneyResponse moneyResponse) {
    moneyRepository.saveAll(moneyResponse.getMoneyMap());
  }
}
