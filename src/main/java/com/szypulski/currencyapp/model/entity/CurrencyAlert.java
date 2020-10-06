package com.szypulski.currencyapp.model.entity;

import com.szypulski.currencyapp.model.enums.AlertType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "alertos")
public class CurrencyAlert {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne()
  private User user;

  @OneToOne()
  private Money to;

  @Column(name = "alert_value")
  private Double alertValue;

  @Column(name = "alert_type")
  private AlertType alertType;
}
