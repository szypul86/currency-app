package com.szypulski.currencyapp.view;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.service.CurrencyAlertManagerService;
import com.szypulski.currencyapp.service.CurrencyAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alerts")
public class AlertController {

  private final CurrencyAlertService currencyAlertService;

  @PostMapping
  public ResponseEntity<AlertDto> save(@RequestBody AlertDto alertDto) {
    return new ResponseEntity<>(currencyAlertService.save(alertDto), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AlertDto> findById(@PathVariable Long id) {
    return new ResponseEntity<>(currencyAlertService.findDtoById(id),HttpStatus.OK);
  }

}
