package com.szypulski.currencyapp.model.entity;

import com.szypulski.currencyapp.model.dto.ExchangeRateDto;

public interface IAllert {

  boolean shouldAlert(ExchangeRateDto exchangeRateDto, Double value);

}
