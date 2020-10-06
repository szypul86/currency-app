package com.szypulski.currencyapp.service.mapper;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.model.entity.CurrencyAlert;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AlertMapper {

  public AlertDto mapEntityToDto(CurrencyAlert currencyAlert) {
    return AlertDto.builder()
        .id(currencyAlert.getId())
        .alertType(currencyAlert.getAlertType())
        .alertValue(currencyAlert.getAlertValue())
        .to(currencyAlert.getTo().getSymbol())
        .userId(currencyAlert.getUser().getId())
        .build();
  }

  public CurrencyAlert mapDtoToEntity(AlertDto dto, Money money, User user) {
    return CurrencyAlert.builder()
        .id(dto.getId())
        .alertType(dto.getAlertType())
        .alertValue(dto.getAlertValue())
        .to(money)
        .user(user)
        .build();
  }

  public List<AlertDto> mapEntitiesToDtos(Collection<CurrencyAlert> alertsCollection) {
    return alertsCollection.stream()
        .map(this::mapEntityToDto)
        .collect(Collectors.toList());
  }
}
