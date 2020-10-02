package com.szypulski.currencyapp.security;

import com.szypulski.currencyapp.model.entity.User;
import com.szypulski.currencyapp.model.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException("User with email: " + email + " does not exist"));
    return new UserPrincipal(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("User with id: " + id + " not found")
    );
    return new UserPrincipal(user);
  }
}
