package com.szypulski.currencyapp.security;

import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("authenticationService")
public class AuthenticationService {

  private final UserService userService;


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


}
