package com.szypulski.currencyapp.controller;

import com.szypulski.currencyapp.service.SendEmailHTML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }

  @GetMapping("/sendMail")
  public String sendMail(){
    SendEmailHTML.sendMail();
    return "email sent";
  }

  @GetMapping("/googled")
  public String hello2(){
    return "to see this text you need to be logged";
  }

}
