package com.szypulski.currencyapp.controller;

import com.szypulski.currencyapp.model.dto.ExchangeRateDto;
import com.szypulski.currencyapp.service.ExchangeRateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

  @GetMapping("/historical/{from}/{to}")
  public List<ExchangeRateDto> findAllPaged(Pageable pageable, @PathVariable String from,
      @PathVariable String to) {
    return exchangeRateService.findAllPagedDtos(pageable, from, to);
  }

  @GetMapping("/{from}/{to}")
  public ExchangeRateDto findNewest(@PathVariable String from, @PathVariable String to) {
    return exchangeRateService.findNewestFromTo(from, to);
  }

}
