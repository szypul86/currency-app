package com.szypulski.currencyapp.security;

import com.szypulski.currencyapp.model.dto.AlertDto;
import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.service.CurrencyAlertService;
import com.szypulski.currencyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service("authenticationService")
public class AuthenticationService {

  private final UserService userService;
  private final CurrencyAlertService alertService;


  public boolean doesUserMatchId(Long userId) {
    UserDto user = userService.findDtoById(userId);

    return user.getEmail().equals(getUsernameFromPrincipal());
  }

  public String getUsernameFromPrincipal() {
    return ((UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal())
        .getUsername();
  }

  public boolean doesUserMatchBodyHiddenId(Long id) {
    Long userId = alertService.findDtoById(id).getUserId();
    UserDto user = userService.findDtoById(userId);
    return user.getEmail().equals(getUsernameFromPrincipal());
  }

  public boolean doesUserMatchBodyHiddenIdAndDtoId(Long id, AlertDto alertDto) {
    long userId = alertService.findDtoById(id).getUserId();
    long dtoId = alertDto.getUserId();
    UserDto user = userService.findDtoById(userId);
    return user.getEmail().equals(getUsernameFromPrincipal()) && userId==dtoId;
  }


}
