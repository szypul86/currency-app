package com.szypulski.currencyapp.model.repository;


import com.szypulski.currencyapp.model.entity.CurrencyAlert;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.enums.AlertType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyAlertRepository extends JpaRepository<CurrencyAlert, Long> {

  List<CurrencyAlert> findAllByAlertType(AlertType type);

  List<CurrencyAlert> findAllByAlertTypeAndTo(AlertType type, Money to);

}
