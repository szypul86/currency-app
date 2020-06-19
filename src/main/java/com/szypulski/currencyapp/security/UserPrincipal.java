package com.szypulski.currencyapp.security;

import com.szypulski.currencyapp.entity.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserPrincipal implements OAuth2User, UserDetails {

  private Long id;
  private String email;
  private String password;
  private boolean active;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipal(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.active = user.isActive();
    this.authorities = Arrays.stream(user.getUserType().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  public UserPrincipal(User user, Map<String, Object> attributes) {
    this(user);
    this.attributes=attributes;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }
}