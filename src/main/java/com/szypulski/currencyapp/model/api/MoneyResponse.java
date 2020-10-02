package com.szypulski.currencyapp.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.szypulski.currencyapp.model.api.deserializer.StringStringMapDeserializer;
import com.szypulski.currencyapp.model.entity.Money;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"success"})
public class MoneyResponse {

  @JsonProperty("symbols")
  @JsonDeserialize(using = StringStringMapDeserializer.class)
  private Set<Money> moneyMap = new HashSet<>();

  @Override
  public String toString() {
    return "MoneyResponse{" +
        "moneyMap=" + moneyMap +
        '}';
  }
}
