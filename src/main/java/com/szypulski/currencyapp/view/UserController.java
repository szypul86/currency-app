package com.szypulski.currencyapp.view;

import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.security.AuthenticationService;
import com.szypulski.currencyapp.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping()
  public ResponseEntity<List<UserDto>> findAllPaged(Pageable pageable) {
    return new ResponseEntity<>(userService.findAllPaged(pageable), HttpStatus.OK);
  }

  @PreAuthorize("@authenticationService.doesUserMatchId(#userId) || hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/{userId}")
  public ResponseEntity<UserDto> findDtoById(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.findDtoById(userId), HttpStatus.OK);
  }

  @PreAuthorize("@authenticationService.doesUserMatchId(#userId) || hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{userId}")
  public ResponseEntity<UserDto> updateById(@PathVariable Long userId, @RequestBody UserDto userDto) {
    return new ResponseEntity<>(userService.updateById(userId, userDto), HttpStatus.OK);
  }

  @PreAuthorize("@authenticationService.doesUserMatchId(#userId) || hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{userId}")
  public ResponseEntity<UserDto> deleteById(@PathVariable Long userId) {
    return new ResponseEntity<>(userService.deleteById(userId), HttpStatus.OK);
  }
}
