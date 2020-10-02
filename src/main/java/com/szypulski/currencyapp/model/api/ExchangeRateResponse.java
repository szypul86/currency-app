package com.szypulski.currencyapp.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.szypulski.currencyapp.model.api.deserializer.StringDoubleMapDeserializer;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponse {

  @JsonDeserialize()
  private Boolean success;
  @JsonDeserialize()
  private Long timestamp;
  @JsonDeserialize()
  private String base;
  @JsonDeserialize()
  private String date;
  @JsonProperty("rates")
  @JsonDeserialize(using = StringDoubleMapDeserializer.class)
  private Map<String, Double> rates = new HashMap<>();


  @Override
  public String toString() {
    return "ExchangeRate{" +
        "success=" + success +
        ", timestamp=" + timestamp +
        ", base='" + base + '\'' +
        ", date='" + date + '\'' +
        ", rates=" + rates +
        '}';
  }
}