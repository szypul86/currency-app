package com.szypulski.currencyapp.model.repository;

import com.szypulski.currencyapp.model.entity.Money;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepository extends JpaRepository<Money, String> {

  List<Money> findBySymbolIn(List<String> symbol);
}
