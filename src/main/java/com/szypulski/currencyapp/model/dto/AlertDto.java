package com.szypulski.currencyapp.model.dto;

import com.szypulski.currencyapp.model.enums.AlertType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AlertDto {

  private Long id;

  private Long userId;

  private String to;

  private Double alertValue;

  private AlertType alertType;
}
