package com.szypulski.currencyapp.model.api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class StringDoubleMapDeserializer extends JsonDeserializer {

  @Override
  public Map<String, Double> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    Map<String, Double> rates = new HashMap<>();
    JsonNode tree = p.readValueAsTree();
    ObjectMapper mapper = new ObjectMapper();
    Iterator<Entry<String, JsonNode>> fields = tree.fields();
    while (fields.hasNext()) {
      Entry<String, JsonNode> field = fields.next();
      rates.put(field.getKey(), Double.valueOf(mapper.writeValueAsString(field.getValue())));
    }
    return rates;
  }
}
