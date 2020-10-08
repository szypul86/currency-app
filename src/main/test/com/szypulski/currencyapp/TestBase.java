package com.szypulski.currencyapp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szypulski.currencyapp.model.entity.ExchangeRate;
import com.szypulski.currencyapp.model.entity.Money;
import com.szypulski.currencyapp.model.repository.ExchangeRateRepository;
import com.szypulski.currencyapp.model.repository.MoneyRepository;
import com.szypulski.currencyapp.service.ExchangeRateService;
import com.szypulski.currencyapp.service.MoneyService;
import com.szypulski.currencyapp.service.mapper.ExchangeRateMapper;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles ="test")
public class TestBase {

  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${base.currency}")
  public String BASE_CURRENCY;

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  ExchangeRateService exchangeRateService;
  @Autowired
  ExchangeRateRepository exchangeRateRepository;
  @Autowired
  private MoneyService moneyService;
  @Autowired
  private MoneyRepository moneyRepository;
  @Autowired
  private ExchangeRateMapper exchangeRateMapper;
  @Autowired
  private RestTemplate restTemplate;

  //@Before()
  public void initialSetUp() {
  /*  MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
  mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
      Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
  restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

  MockRestServiceServer.MockRestServiceServerBuilder builder =
      MockRestServiceServer.bindTo(restTemplate);
  builder.ignoreExpectOrder(true);

  MockRestServiceServer server = builder.build();

  MoneyResponse moneyResponse = new MoneyResponse();
  moneyResponse.setMoneyMap(Set.of(new Money("USD", "zielone"), new Money("PLN", "złocisze"), new Money("EUR","jurki"), new Money("GBP", "funciole")));

  server.expect(ExpectedCount.manyTimes(),
      requestTo(new URI("http://data.fixer.io/api/symbols?access_key=" + API_KEY)))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
          .body(mapper.writeValueAsString(moneyResponse)));

  String base = "EUR";
  String symbols = "USD,PLN,GBP";
  String url =
      "http://data.fixer.io/api/latest?access_key=" + API_KEY + "&base=" + base + "&symbols="
          + symbols;

  server.expect(ExpectedCount.manyTimes(),
      requestTo(new URI(url)))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
          .body(mapper.writeValueAsString(moneyResponse)));*/
  }

  @BeforeEach
  public void setUp() throws URISyntaxException, JsonProcessingException {

    exchangeRateRepository.deleteAll();
    moneyRepository.deleteAll();

  }

  public void saveDefaultMoneys(){
    moneyRepository.saveAll(List.of(
        new Money(BASE_CURRENCY, BASE_CURRENCY +"aski"),
        new Money("USD", "zielone"),
        new Money("PLN", "złocisze"),
        new Money("GBP","funie")));
  }

  public void saveExchangeRate(String from, String to, Double value, int milisecFromBaseTimestamp){
    Money moneyFrom = moneyRepository.findById(from).orElse(new Money(from, from + "ki"));
    Money moneyTo = moneyRepository.findById(to).orElse(new Money(to, to + "ki"));
    ExchangeRate exr = ExchangeRate.builder()
        .from(moneyFrom)
        .to(moneyTo)
        .timestamp(123456789L-milisecFromBaseTimestamp)
        .value(value)
        .build();
    exchangeRateRepository.save(exr);
  }
}
