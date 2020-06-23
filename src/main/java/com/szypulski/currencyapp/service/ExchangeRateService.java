package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.entity.ExchangeRate;
import com.szypulski.currencyapp.repository.ExchangeRateRepository;
import com.szypulski.currencyapp.api.ExchangeRateResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;

  private final MoneyService moneyService;


  public ExchangeRateService(ExchangeRateRepository exchangeRateRepository,
      MoneyService moneyService) {
    this.exchangeRateRepository = exchangeRateRepository;
    this.moneyService = moneyService;
  }

  public void saveExchangeRatesFromSingleExchangeRateResponse(ExchangeRateResponse response) {
    Set<ExchangeRate> setToBeSaved = mapApiResponseToExchangeRates(response);

    Set<ExchangeRate> setFromDatabase = exchangeRateRepository
        .findAllByTimestampAndFrom(response.getTimestamp(),
            moneyService.findBySymbol(response.getBase()));

    if (setFromDatabase.size() > 0) {
      setToBeSaved = getExchangeRatesWithoutExistingRates(setToBeSaved,
          setFromDatabase);
    }
    exchangeRateRepository.saveAll(setToBeSaved);
  }

  private Set<ExchangeRate> getExchangeRatesWithoutExistingRates(Set<ExchangeRate> setToBeSaved,
      Set<ExchangeRate> setFromDatabase) {

    return setToBeSaved.stream()
        .filter(exchangeRate ->
            !setFromDatabase.stream()
                .map(ExchangeRate::getTo)
                .collect(Collectors.toList())
                .contains(exchangeRate.getTo()))
        .collect(Collectors.toSet());
  }

  private Set<ExchangeRate> mapApiResponseToExchangeRates(ExchangeRateResponse response){
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
