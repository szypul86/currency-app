package com.szypulski.currencyapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name")
  @NotNull
  private String name;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "email")
  @NotNull
  private String email;

  @Column(name = "encoded_password")
  @JsonIgnore
  private String password;

  @Column(name = "user_type")
  @NotNull
  private String userType;

  @Column(name = "active")
  private boolean active;

  @Column(name = "email_verified")
  @NotNull
  private Boolean emailVerified = false;

  @Column(name = "auth_provider")
  @NotNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  @Column(name = "auth_provider_id")
  private String providerId;

}
