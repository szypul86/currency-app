package com.szypulski.currencyapp.service.scheduler;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.service.IObservable;
import com.szypulski.currencyapp.service.IObserver;
import com.szypulski.currencyapp.service.rest.FixerRestService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeRateDownloadScheduler implements IObservable {

  private final FixerRestService fixerRestService;

  private final List<IObserver> observers = new ArrayList<>();

  @Scheduled(fixedRate = 3600000)
  public void getLatestExchangeRate() {
    ExchangeRateResponse err = fixerRestService.getLatest("EUR", "USD,PLN,GBP");
    log.info("ExchangeRate successfully added to db: {}", err.toString());

  }

  @Override
  public void addObserver(IObserver iObserver) {
    observers.add(iObserver);
  }

  @Override
  public void removeObserver(IObserver iObserver) {
    observers.remove(iObserver);
  }

  @Override
  public void inform() {
    this.observers.forEach(IObserver::update);
  }


}
