package com.szypulski.currencyapp.service.mapper;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.model.dto.ExchangeRateDto;
import com.szypulski.currencyapp.model.entity.ExchangeRate;
import com.szypulski.currencyapp.service.MoneyService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateMapper {

  private final MoneyService moneyService;

  public ExchangeRateMapper(MoneyService moneyService) {
    this.moneyService = moneyService;
  }

  public Set<ExchangeRate> mapApiResponseToEntities(ExchangeRateResponse response) {
    Set<ExchangeRate> resultList = new HashSet<>();
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

  public ExchangeRateDto mapEntityToView(ExchangeRate er) {
    ExchangeRateDto erd =  ExchangeRateDto.builder()
        .id(er.getId())
        .date(new Date(er.getTimestamp()))
        .from(er.getFrom().getSymbol())
        .to(er.getTo().getSymbol())
        .value(er.getValue())
        .build();
    return erd;
  }

  public List<ExchangeRateDto> mapEntitiesToViews(Page<ExchangeRate> ers) {
    return ers.stream()
        .map(this::mapEntityToView)
        .collect(Collectors.toList());
  }
}
