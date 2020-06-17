package com.szypulski.currencyapp.controller;

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

  @GetMapping("/googled")
  public String hello2(){
    return "to see this text you need to be logged";
  }

}
