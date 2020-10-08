package com.szypulski.currencyapp.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "money")
public class Money {

  @Id
  private String symbol;

  private String description;

  @Override
  public String toString() {
    return "Money{" +
        "symbol='" + symbol + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
