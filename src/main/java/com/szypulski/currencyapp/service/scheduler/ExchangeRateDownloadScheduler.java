package com.szypulski.currencyapp.service.scheduler;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.service.IMapObservable;
import com.szypulski.currencyapp.service.IMapObserver;
import com.szypulski.currencyapp.service.rest.FixerRestService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeRateDownloadScheduler implements IMapObservable {

  private final FixerRestService fixerRestService;

  private final List<IMapObserver> observers = new ArrayList<>();

  private String BASE_CURRENCY = "EUR";
  private String CURRENCY_SYMBOLS = "USD,PLN,GBP";

  @Scheduled(fixedRate = 360000)
  public void getLatestExchangeRate() {

    try {
      ExchangeRateResponse err = fixerRestService.getLatest(BASE_CURRENCY, CURRENCY_SYMBOLS);
      log.info("ExchangeRate successfully added to db: {}", err.toString());
      inform(err.getRates());
    } catch (Exception e) {
      log.error("Connection to currency supplier failed");
      throw new RuntimeException("failed to connect currency supplier"
      );
    }

  }

  @Override
  public void addObserver(IMapObserver iObserver) {
    observers.add(iObserver);
  }

  @Override
  public void removeObserver(IMapObserver iObserver) {
    observers.remove(iObserver);
  }

  @Override
  public void inform(Map<String, Double> stringDoubleMap) {
    this.observers.forEach(o -> o.update(stringDoubleMap));
    /*for (IMapObserver observer : this.observers) {
      observer.update(stringDoubleMap);
    }*/
  }


}
