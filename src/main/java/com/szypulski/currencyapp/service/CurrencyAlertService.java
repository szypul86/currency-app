package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.model.entity.CurrencyAlert;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.model.enums.AlertType;
import com.szypulski.currencyapp.model.repository.CurrencyAlertRepository;
import com.szypulski.currencyapp.service.mapper.AlertMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrencyAlertService {

  private final CurrencyAlertRepository currencyAlertRepository;
  private final UserService userService;
  private final MoneyService moneyService;
  private final AlertMapper alertMapper;


  public List<AlertDto> findAllDtosByType(AlertType type) {
    return alertMapper.mapEntitiesToDtos(currencyAlertRepository.findAllByAlertType(type));
  }

  public List<AlertDto> findAllDtosByTypeAndTo(AlertType type, String to) {
    Money money = moneyService.findBySymbol(to);
    return alertMapper.mapEntitiesToDtos(currencyAlertRepository.findAllByAlertTypeAndTo(type, money));
  }

  public AlertDto save(AlertDto alertDto) {
    User user = userService.findById(alertDto.getUserId());
    Money money = moneyService.findBySymbol(alertDto.getTo());
    CurrencyAlert currencyAlert = currencyAlertRepository
        .save(alertMapper.mapDtoToEntity(alertDto, money, user));
    return alertMapper.mapEntityToDto(currencyAlert);
  }

  public AlertDto deleteById(Long id) {
    CurrencyAlert currencyAlert = findById(id);
    currencyAlertRepository.deleteById(id);
    return alertMapper.mapEntityToDto(currencyAlert);
  }

  public AlertDto findDtoById(Long id) {
    return alertMapper.mapEntityToDto(findById(id));
  }

  protected CurrencyAlert findById(Long id) {
    return currencyAlertRepository.findById(id).orElseThrow();
  }
}
