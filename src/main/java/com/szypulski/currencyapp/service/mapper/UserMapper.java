package com.szypulski.currencyapp.service.mapper;

import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.model.entity.AuthProvider;
import com.szypulski.currencyapp.model.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

  private final BCryptPasswordEncoder encoder;

  public User mapUserDtoToUser(UserDto userDto) {
    return User.builder()
        .name(userDto.getUserName())
        .email(userDto.getEmail())
        .password(encoder.encode(userDto.getPassword()))
        .active(userDto.isActive())
        .userType(String.join(",", userDto.getTypes()))
        .provider(AuthProvider.local)
        .build();
  }

  public User mapUserDtoToUser(UserDto userDto, User user) {
    user.setName(userDto.getUserName());
    user.setEmail(userDto.getEmail());
    user.setPassword(encoder.encode(userDto.getPassword()));
    user.setActive(userDto.isActive());
    user.setUserType(String.join(",", userDto.getTypes()));
    user.setProvider(AuthProvider.local);
    return user;
  }

  public UserDto mapUserToUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .userName(user.getName())
        .email(user.getEmail())
        .active(user.isActive())
        .types(Stream.of(user.getUserType().split(",")).collect(Collectors.toList()))
        .build();
  }

  public List<UserDto> mapEntityPageToDtoList(Page<User> userPage) {
    return userPage.stream()
        .map(this::mapUserToUserDto)
        .collect(Collectors.toList());
  }

}
