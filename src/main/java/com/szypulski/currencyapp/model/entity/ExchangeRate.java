package com.szypulski.currencyapp.model.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "exchange_rate")
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long timestamp;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH,
      CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "base_money_symbol", referencedColumnName = "symbol")
  private Money from;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH,
      CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "exchange_money_symbol", referencedColumnName = "symbol")
  private Money to;

  private Double value;


}
