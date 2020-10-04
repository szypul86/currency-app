package com.szypulski.currencyapp.model.dto;

import com.szypulski.currencyapp.model.enums.AlertType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AllertDto {

  private final Long id;

  private final Long userId;

  private final String to;

  private final Double alertValue;

  private final AlertType alertType;
}
