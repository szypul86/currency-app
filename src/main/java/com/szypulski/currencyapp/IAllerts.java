package com.szypulski.currencyapp;

public interface IAllerts {

  boolean achievedValue(String baseSymbol, String exchangeSymbol,  Double value);

  boolean achievedPercentGain(String baseSymbol, String exchangeSymbol,  Double value, Double baseValue);

}
