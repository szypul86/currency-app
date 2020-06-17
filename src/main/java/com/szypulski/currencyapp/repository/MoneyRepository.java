package com.szypulski.currencyapp.repository;

import com.szypulski.currencyapp.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepository extends JpaRepository<Money, String> {

}
