package com.szypulski.currencyapp.exceptions;

import com.szypulski.currencyapp.model.dto.UserMessageView;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  @ResponseBody
  public ResponseEntity<String> noSuchElementException(NoSuchElementException e) {
    log.info("No such element exception occurred ", e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }


}
