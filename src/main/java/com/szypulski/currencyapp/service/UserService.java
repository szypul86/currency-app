package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.model.dto.UserDto;
import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.model.repository.UserRepository;
import com.szypulski.currencyapp.security.MyUserDetails;
import com.szypulski.currencyapp.service.mapper.UserMapper;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;


  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByName(username);
    user.orElseThrow(
        () -> new UsernameNotFoundException("User with username: " + username + "not found"));
    return new MyUserDetails(user.get());
  }

  public User save(UserDto userDto) {
    Optional<User> optionalUser = userRepository
        .findByEmail(userDto.getEmail());
    if (optionalUser.isPresent()) {
      throw new IllegalArgumentException(
          "User with email: " + optionalUser.get().getEmail() + " already exists. Try to login by: "
              + optionalUser.get().getProviderId());
    } else {
      User newUser = userMapper.mapUserDtoToUser(userDto);
      return userRepository.save(newUser);
    }
  }

  public List<UserDto> findAllPaged(Pageable pageable) {
    Page<User> userPage = userRepository.findAll(pageable);
    return userMapper.mapEntityPageToDtoList(userPage);
  }

  public UserDto findDtoById(Long userId) {
    return userMapper.mapUserToUserDto(findById(userId));
  }

  protected User findById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id: " + userId + "not found"));
  }

  public UserDto updateById(Long userId, UserDto userDto) {
    User user = findById(userId);
    User updatedUser = userMapper.mapUserDtoToUser(userDto, user);
    userRepository.save(updatedUser);
    return userMapper.mapUserToUserDto(updatedUser);
  }

  public UserDto deleteById(Long userId) {
    User user = findById(userId);
    UserDto userDto = userMapper.mapUserToUserDto(user);
    userRepository.delete(user);
    return userDto;
  }


}
