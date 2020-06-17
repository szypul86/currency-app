package com.szypulski.currencyapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"", indexes = {@Index(name = "user_email", columnList = "email")})
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name")
  @NotNull
  private String userName;

  @Column(name = "email")
  @NotNull
  private String email;

  @Column (name="password")
  @JsonIgnore
  private String password;

  @Column(name = "user_type")
  @NotNull
  private String userType;

  private boolean active;

}
