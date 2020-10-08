package com.szypulski.currencyapp.model.dto;

import org.springframework.http.HttpStatus;

public class UserMessageView {

  private String message;
  private HttpStatus httpStatus;

  public UserMessageView() {
  }

  public UserMessageView(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
