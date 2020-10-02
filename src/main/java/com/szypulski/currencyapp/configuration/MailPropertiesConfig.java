package com.szypulski.currencyapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.smtp")
public class MailPropertiesConfig {

  private SslConfig ssl;
  private SaslConfig sasl;
  private AuthConfig auth;
  private String mailFrom;
  private String mailCc;

  public SslConfig getSsl() {
    return ssl;
  }

  public void setSsl(SslConfig ssl) {
    this.ssl = ssl;
  }

  public SaslConfig getSasl() {
    return sasl;
  }

  public void setSasl(SaslConfig sasl) {
    this.sasl = sasl;
  }

  public AuthConfig getAuth() {
    return auth;
  }

  public void setAuth(AuthConfig auth) {
    this.auth = auth;
  }

  public String getMailFrom() {
    return mailFrom;
  }

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public String getMailCc() {
    return mailCc;
  }

  public void setMailCc(String mailCc) {
    this.mailCc = mailCc;
  }

  public static class SslConfig {

    private boolean enable;

    public boolean isEnable() {
      return enable;
    }

    public void setEnable(boolean enable) {
      this.enable = enable;
    }
  }

  public static class SaslConfig {

    private boolean enable;
    private String mechanisms;

    public String getMechanisms() {
      return mechanisms;
    }

    public void setMechanisms(String mechanisms) {
      this.mechanisms = mechanisms;
    }

    public boolean isEnable() {
      return enable;
    }

    public void setEnable(boolean enable) {
      this.enable = enable;
    }
  }

  public static class AuthConfig {

    private LoginConfig login;
    private PlainConfig plain;

    public LoginConfig getLogin() {
      return login;
    }

    public void setLogin(LoginConfig login) {
      this.login = login;
    }

    public PlainConfig getPlain() {
      return plain;
    }

    public void setPlain(PlainConfig plain) {
      this.plain = plain;
    }

    public static class LoginConfig {

      private boolean disable;

      public boolean isDisable() {
        return disable;
      }

      public void setDisable(boolean disable) {
        this.disable = disable;
      }
    }

    public static class PlainConfig {

      private boolean disable;

      public boolean isDisable() {
        return disable;
      }

      public void setDisable(boolean disable) {
        this.disable = disable;
      }
    }


  }
}
