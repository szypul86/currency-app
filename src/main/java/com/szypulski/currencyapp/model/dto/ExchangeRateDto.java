package com.szypulski.currencyapp.model.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateDto {

  private Long id;

  private Date date;

  private String from;

  private String to;

  private Double value;

}

