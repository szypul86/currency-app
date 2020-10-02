package com.szypulski.currencyapp.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String userName;
  private String email;
  private String password;
  private boolean active;
  private List<String> types;
}

