package com.szypulski.currencyapp.service;

import com.szypulski.currencyapp.configuration.GmailOauthPropertiesConfig;
import com.szypulski.currencyapp.dto.GoogleOauthResponse;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleAuthenticationService {

  private final GmailOauthPropertiesConfig gmailOauthPropertiesConfig;

  public GoogleAuthenticationService(
      GmailOauthPropertiesConfig gmailOauthPropertiesConfig) {
    this.gmailOauthPropertiesConfig = gmailOauthPropertiesConfig;
  }

  public String obtainAccessToken() {

    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("client_id", gmailOauthPropertiesConfig.getClientId());
    requestParams.add("client_secret", gmailOauthPropertiesConfig.getClientSecret());
    requestParams.add("refresh_token", gmailOauthPropertiesConfig.getRefreshToken());
    requestParams.add("grant_type", "refresh_token");

    GoogleOauthResponse response = restTemplate
        .postForObject(gmailOauthPropertiesConfig.getTokenUrl(),
            requestParams,
            GoogleOauthResponse.class);

    return Objects.requireNonNull(response).getAccessToken();
  }
}
