package com.solo.security.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: solo
 * @Date: 2019/9/25 8:08 PM
 * @Version 1.0
 */
@ControllerAdvice
public class ExceptionHandle {

  @ExceptionHandler(UserNotExistException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleUserNotExistException(UserNotExistException exception){
    Map<String, Object> map = new HashMap<>();
    map.put("id", exception.getId());
    map.put("message", exception.getMessage());
    return map;
  }
}
