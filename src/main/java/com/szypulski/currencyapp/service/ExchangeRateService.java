package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.api.ExchangeRateResponse;
import com.szypulski.currencyapp.model.dto.ExchangeRateDto;
import com.szypulski.currencyapp.model.entity.ExchangeRate;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.repository.ExchangeRateRepository;
import com.szypulski.currencyapp.service.mapper.ExchangeRateMapper;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;
  private final MoneyService moneyService;
  private final ExchangeRateMapper exchangeRateMapper;

  private String BASE_CURRENCY = "EUR";


  public List<ExchangeRateDto> findAllPagedDtos(Pageable pageable, String from, String to) {
    if (List.of(from, to).contains(BASE_CURRENCY)) {
      return findAllBaseCurrencyFromTo(pageable, from, to);
    }
    return findAllNonBaseCurrencyFromTo(pageable, from, to);
  }

  private List<ExchangeRateDto> findAllNonBaseCurrencyFromTo(Pageable pageable, String from, String to) {
    List<Money> moneys = moneyService.findBySymbolIn(List.of(from, to));
    Money moneyFrom = filterMoney(from, moneys);
    Money moneyTo = filterMoney(to, moneys);
    Page<ExchangeRate> froms = exchangeRateRepository
        .findAllByToOrderByTimestampDesc(pageable, moneyFrom);
    Page<ExchangeRate> tos = exchangeRateRepository
        .findAllByToOrderByTimestampDesc(pageable, moneyTo);
    return mapNonBaseCurrencyEntityPagesToDtos(froms, tos);
  }

  private List<ExchangeRateDto> mapNonBaseCurrencyEntityPagesToDtos(Page<ExchangeRate> froms,
      Page<ExchangeRate> tos) {
    return froms.stream()
        .map(exchangeRateFrom -> mapNonBaseCurrencyEntityToDto(exchangeRateFrom, tos.stream()
            .filter(t -> t.getTimestamp().equals(exchangeRateFrom.getTimestamp()))
            .findAny()
            .orElseThrow()))
        .collect(Collectors.toList());
  }

  private Money filterMoney(String from, List<Money> moneys) {
    return moneys.stream().filter(m -> m.getSymbol().equals(from)).findAny().orElseThrow();
  }

  private List<ExchangeRateDto> findAllBaseCurrencyFromTo(Pageable pageable, String from, String to) {
    if (from.equals(BASE_CURRENCY)) {
      return findAllFromBaseCurrencyToDtos(pageable, to);
    }
    return findAllFromBaseCurrencyToDtos(pageable, from).stream()
        .map(erdto -> invertFromTo(erdto, from, to))
        .collect(Collectors.toList());
  }

  private List<ExchangeRateDto> findAllFromBaseCurrencyToDtos(Pageable pageable, String to) {
    Money money = moneyService.findBySymbol(to);
    return exchangeRateMapper.mapEntitiesToViews(exchangeRateRepository
        .findAllByToOrderByTimestampDesc(pageable, money));
  }

  public ExchangeRateDto findNewestFromTo(String from, String to) {
    if (List.of(from, to).contains(BASE_CURRENCY)) {
      return findNewestBaseCurrencyFromTo(from, to);
    }
    return findNewestNonBaseCurrencyFromTo(from, to);
  }

  private ExchangeRateDto findNewestBaseCurrencyFromTo(String from, String to) {
    if (from.equals(BASE_CURRENCY)) {
      return findNewestBySymbolTo(to);
    }
    return invertFromTo(findNewestBySymbolTo(from), from, to);
  }

  private ExchangeRateDto findNewestBySymbolTo(String symbol) {
    Money money = moneyService.findBySymbol(symbol);
    return exchangeRateMapper.mapEntityToView(exchangeRateRepository
        .findFirstByToOrderByTimestampDesc(money));
  }

  private ExchangeRateDto invertFromTo(ExchangeRateDto exchangeRateDto, String from, String to) {
    exchangeRateDto.setTo(from);
    exchangeRateDto.setFrom(to);
    exchangeRateDto.setValue(1 / exchangeRateDto.getValue());
    return exchangeRateDto;
  }


  private ExchangeRateDto findNewestNonBaseCurrencyFromTo(String from, String to) {
    List<Money> moneys = moneyService.findBySymbolIn(List.of(from, to));
    List<ExchangeRate> rates = exchangeRateRepository.findFirst2ByToInOrderByTimestampDesc(moneys);
    ExchangeRate rateFrom = filterExchangeRate(from, rates);
    ExchangeRate rateTo = filterExchangeRate(to, rates);
    return mapNonBaseCurrencyEntityToDto(rateFrom, rateTo);
  }

  private ExchangeRateDto mapNonBaseCurrencyEntityToDto(ExchangeRate rateFrom, ExchangeRate rateTo) {
    return ExchangeRateDto.builder()
        .date(new Date(rateFrom.getTimestamp()))
        .from(rateFrom.getTo().getSymbol())
        .to(rateTo.getTo().getSymbol())
        .value(rateTo.getValue() / rateFrom.getValue())
        .build();
  }

  private ExchangeRate filterExchangeRate(String from, List<ExchangeRate> rates) {
    return rates.stream()
        .filter(er -> er.getTo().getSymbol().equals(from))
        .findAny()
        .orElseThrow();
  }

  @Transactional
  public void saveExchangeRatesFromSingleExchangeRateResponse(ExchangeRateResponse response) {
    Set<ExchangeRate> setToBeSaved = exchangeRateMapper.mapApiResponseToEntities(response);
    Money money = moneyService.findBySymbol(response.getBase());

    Set<ExchangeRate> setFromDatabase = exchangeRateRepository
        .findAllByTimestampAndFrom(response.getTimestamp(),
            money);

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
}
