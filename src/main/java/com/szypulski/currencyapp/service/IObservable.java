package com.szypulski.currencyapp.service;

public interface IObservable {

  void addObserver(IObserver iObserver);

  void removeObserver(IObserver iObserver);

  void inform();

}
