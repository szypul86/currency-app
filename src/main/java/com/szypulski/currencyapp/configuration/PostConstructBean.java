package com.szypulski.currencyapp.configuration;


import com.szypulski.currencyapp.service.rest.FixerRestService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Profile("!test")
public class PostConstructBean {

  private final FixerRestService fixerRestService;

  @PostConstruct
  private void saveSymbols() {
    fixerRestService.saveSymbols();
  }
}
