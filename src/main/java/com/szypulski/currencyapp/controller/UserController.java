package com.szypulski.currencyapp.controller;

import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping()
  public ResponseEntity<User> save(@RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.save(userDto), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{userId}")
  public ResponseEntity<UserDto> deleteById(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.deleteById(userId), HttpStatus.OK);
  }

  @GetMapping(value = "/{userId}")
  public ResponseEntity<UserDto> findDtoById(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.findDtoById(userId), HttpStatus.OK);
  }

}
