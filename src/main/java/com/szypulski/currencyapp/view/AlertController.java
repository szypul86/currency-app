package com.szypulski.currencyapp.view;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.service.CurrencyAlertManagerService;
import com.szypulski.currencyapp.service.CurrencyAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alerts")
public class AlertController {

  private final CurrencyAlertManagerService currencyAlertManagerService;
  private final CurrencyAlertService currencyAlertService;
  //private final UserService userService;
  //private final ExchangeRateDownloadScheduler scheduler;

  @PostMapping
  public AlertDto save(@RequestBody AlertDto alertDto) {
    return currencyAlertService.save(alertDto);
  }

  @GetMapping("/{id}")
  public AlertDto findById(@PathVariable Long id) {
    return currencyAlertService.findDtoById(id);
  }

  /*@GetMapping("/manager/{currency}")
  public String createManager(@PathVariable String currency){
  CurrencyAlertManager manager = new CurrencyAlertManager(alertService,userService);
  manager.setCurrency(currency);
  manager.subscribe(scheduler);
  return "manager created";
  }*/

  @GetMapping("/manager/{currency}")
  public void createManager(@PathVariable String currency) {
    currencyAlertManagerService.createManager(currency);
  }
}
