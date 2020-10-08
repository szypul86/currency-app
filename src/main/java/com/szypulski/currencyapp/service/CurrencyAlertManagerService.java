package com.szypulski.currencyapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyAlertManagerService {

  private final CurrencyAlertService currencyAlertService;
  private final UserService userService;
  private final IMapObservable observableScheduler;

  public void createManager(String currency) {
    CurrencyAlertManager manager = new CurrencyAlertManager(currencyAlertService, userService);
    manager.setCurrency(currency);
    log.info("{}CurrencyManager created", currency);
    manager.subscribe(observableScheduler);
    log.info("{}CurrencyManager subscribed to {}", currency, observableScheduler.getClass());
  }
}
