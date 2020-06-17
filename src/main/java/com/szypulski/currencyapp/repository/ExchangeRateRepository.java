package com.szypulski.currencyapp.repository;

import com.szypulski.currencyapp.entity.ExchangeRate;
import com.szypulski.currencyapp.entity.Money;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {

  Set<ExchangeRate> findAllByTimestampAndFrom(Long timestamp, Money from);
}
