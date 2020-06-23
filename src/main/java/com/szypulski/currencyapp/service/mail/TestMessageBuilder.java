package com.szypulski.currencyapp.service.mail;

public class TestMessageBuilder implements MailMessageBuilder {

  @Override
  public String buildMessage() {
    return "<br><br>This is test message<br><br>";
  }
}
