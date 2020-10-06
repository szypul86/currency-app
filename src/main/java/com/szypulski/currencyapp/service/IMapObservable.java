package com.szypulski.currencyapp.service;

import java.util.Map;

public interface IMapObservable {

  void addObserver(IMapObserver iObserver);

  void removeObserver(IMapObserver iObserver);

  void inform(Map<String, Double> stringDoubleMap);

}
