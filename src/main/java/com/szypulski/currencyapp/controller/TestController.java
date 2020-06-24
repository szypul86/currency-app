package com.szypulski.currencyapp.controller;

import com.szypulski.currencyapp.service.mail.MailMessageBuilder;
import com.szypulski.currencyapp.service.mail.MailService;
import com.szypulski.currencyapp.service.mail.TestMessageBuilder;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

  private final MailService mailService;


  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/sendMail")
  public String sendMail() throws MessagingException {
    MailMessageBuilder testMessageBuilder = new TestMessageBuilder();
    mailService.send("szypul86@gmail.com", "tescik", testMessageBuilder);
    return "email sent";
  }

  @GetMapping("/googled")
  public String hello2() {
    return "to see this text you need to be logged";
  }

}
