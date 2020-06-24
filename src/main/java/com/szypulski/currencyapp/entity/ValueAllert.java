package com.szypulski.currencyapp.entity;

import com.szypulski.currencyapp.service.IObservable;
import com.szypulski.currencyapp.service.IObserver;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ValueAllert implements IAllert, IObserver, IObservable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH,
      CascadeType.REMOVE},fetch = FetchType.LAZY)
  @JoinColumn(name = "base_money_symbol", referencedColumnName = "symbol")
  private ExchangeRate exchangeRate;

  private double allertValue;


  @Override
  public boolean shouldAllert(Double value) {
    return (exchangeRate.getValue() > value);
  }



  @Override
  public void addObserver(IObserver iObserver) {

  }

  @Override
  public void removeObserver(IObserver iObserver) {

  }

  @Override
  public void inform() {

  }

  @Override
  public void update() {

  }
}
