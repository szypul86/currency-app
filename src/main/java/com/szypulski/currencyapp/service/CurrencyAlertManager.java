package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.model.enums.AlertType;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Data
@Service
public class CurrencyAlertManager implements IMapObserver {

  private final CurrencyAlertService currencyAlertService;
  private final UserService userService;
  //private final MailService mailService;

  private String currency = "USD";
  private AlertType type = AlertType.UNDER;

  public void subscribe(IMapObservable observable) {
    observable.addObserver(this);
  }

  @Override
  public void update(Map<String, Double> stringDoubleMap) {
    if (stringDoubleMap.containsKey(currency)) {
      List<AlertDto> alerts = currencyAlertService.findAllDtosByTypeAndTo(type, currency);
      alerts.stream()
          .filter(a -> a.getTo().equals(currency))
          .filter(a -> a.getAlertValue() < stringDoubleMap.get(currency))
          .map(a -> userService.findDtoById(a.getUserId()))
          .forEach(userDto -> {
                System.out
                    .println("e-mail will be sent to user with id: " + userDto.getId() + " to e-mail: "
                        + userDto.getEmail() + " because " + currency + " reached value " + type.name()
                        + " " + stringDoubleMap.get(currency) + " that was: "
                        + alerts.stream()
                        .filter(a -> a.getUserId().equals(userDto.getId()))
                        .filter(a -> a.getAlertValue() < stringDoubleMap.get(currency))
                        .map(AlertDto::getAlertValue).toString());
                /*try {
                  mailService.send(userDto.getEmail(),"Currency Alert!", new CurrencyAlertMessageBuilder());
                } catch (MessagingException e) {
                  e.printStackTrace();
                }*/
              }
          );
    }
  }
}

