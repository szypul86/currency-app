package com.szypulski.currencyapp.model.entity;

import com.szypulski.currencyapp.model.enums.AlertType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Alert {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH,
      CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private User user;

  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH,
      CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private Money to;

  @Column(name = "alert_value")
  private Double alertValue;

  @Column(name = "alert_type")
  private AlertType alertType;
}
