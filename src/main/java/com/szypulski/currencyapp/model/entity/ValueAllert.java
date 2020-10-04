package com.szypulski.currencyapp.model.entity;

import com.szypulski.currencyapp.model.dto.ExchangeRateDto;
import com.szypulski.currencyapp.service.ExchangeRateService;
import com.szypulski.currencyapp.service.IObservable;
import com.szypulski.currencyapp.service.IObserver;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;

/*@Entity*/
@RequiredArgsConstructor
public class ValueAllert implements IAllert, IObserver, IObservable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;

  private final ExchangeRateService exchangeRateService;

  private final String to;

  private final double alertValue;

  private final List<IObserver> observers = new ArrayList<>();

  @Override
  public boolean shouldAlert(ExchangeRateDto exchangeRateDto, Double value) {
    return (exchangeRateDto.getValue() > value);
  }

  @Override
  public void addObserver(IObserver iObserver) {

  }

  @Override
  public void removeObserver(IObserver iObserver) {
  }

  @Override
  public void inform()  {
    this.observers.forEach(IObserver::update);
  }


  @Override
  public void update() {
    ExchangeRateDto exchangeRateDto = exchangeRateService.findNewestFromTo("EUR",to);
    if (shouldAlert(exchangeRateDto,this.alertValue)){
      inform();
    }

  }
}
