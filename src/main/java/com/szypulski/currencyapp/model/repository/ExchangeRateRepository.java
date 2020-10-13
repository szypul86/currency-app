package com.szypulski.currencyapp.model.repository;

import com.szypulski.currencyapp.model.entity.ExchangeRate;
import com.szypulski.currencyapp.model.entity.Money;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

  Set<ExchangeRate> findAllByTimestampAndFrom(Long timestamp, Money from);

  Page<ExchangeRate> findAllByToOrderByTimestampDesc(Pageable pageable, Money to);

  ExchangeRate findFirstByToOrderByTimestampDesc(Money to);

  List<ExchangeRate> findFirst2ByToInOrderByTimestampDesc(List<Money> to);

  Page<ExchangeRate> findAll(Pageable pageable);

  List<ExchangeRate> deleteAllByTimestampBefore(Long timestamp);
}
