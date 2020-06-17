package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.UserRepository;
import com.szypulski.currencyapp.dto.UserDto;
import com.szypulski.currencyapp.entity.User;
import com.szypulski.currencyapp.security.MyUserDetails;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder encoder;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUserName(username);
    user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    return new MyUserDetails(user.get());
  }

  public User save(UserDto userDto) {

    Optional<User> optionalUser = userRepository
        .findByEmail(userDto.getEmail());

    if (optionalUser.isPresent()) {
      throw new IllegalArgumentException("User with email: " + optionalUser.get().getEmail() + " already exists");
    }
    else {
      User newUser = mapUserDtoToUser(userDto);
      return userRepository.save(newUser);
    }
  }

  public UserDto deleteById(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("Not found: " + userId));

    UserDto userDto = mapUserToUserDto(user);
    userRepository.delete(user);
    return userDto;
  }

  private User mapUserDtoToUser(UserDto userDto){
    return User.builder()
        .userName(userDto.getUserName())
        .email(userDto.getEmail())
        .password(encoder.encode(userDto.getPassword()))
        .active(userDto.isActive())
        .userType(String.join(",", userDto.getTypes()))
        .build();
  }

  private UserDto mapUserToUserDto(User user){
    return UserDto.builder()
        .id(user.getId())
        .userName(user.getUserName())
        .email(user.getEmail())
        .active(user.isActive())
        .types(Stream.of(user.getUserType().split(",")).collect(Collectors.toList()))
        .build();
  }

}
