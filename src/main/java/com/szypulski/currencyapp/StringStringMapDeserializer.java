package com.szypulski.currencyapp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szypulski.currencyapp.entity.Money;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class StringStringMapDeserializer extends JsonDeserializer {
  @Override
  public Set<Money> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    Set<Money> rates = new HashSet<>();
    JsonNode tree = p.readValueAsTree();
    ObjectMapper mapper = new ObjectMapper();
    Iterator<Entry<String, JsonNode>> fields = tree.fields();
    while (fields.hasNext()) {
      Entry<String, JsonNode> field = fields.next();
      rates.add(new Money(field.getKey(), mapper.writeValueAsString(field.getValue())));
    }
    return rates;
  }
  /*@Override
  public Map deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    Map<String, String> rates = new HashMap<>();
    JsonNode tree = p.readValueAsTree();
    ObjectMapper mapper = new ObjectMapper();
    Iterator<Entry<String, JsonNode>> fields = tree.fields();
    while (fields.hasNext()) {
      Entry<String, JsonNode> field = fields.next();
      rates.put(field.getKey(),mapper.writeValueAsString(field.getValue()));
    }
    return rates;
  }*/
}
