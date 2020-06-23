package com.szypulski.currencyapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "oauth.gmail")
public class GmailOauthPropertiesConfig {

  private String clientId;
  private String clientSecret;
  private String refreshToken;
  private String tokenUrl;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getTokenUrl() {
    return tokenUrl;
  }

  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

}