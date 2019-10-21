package com.solo.security.exception;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/9/25 8:07 PM
 * @Version 1.0
 */
@Data
public class UserNotExistException extends RuntimeException {

  private Integer id;

  public UserNotExistException(int id){
    super("user not exist");
    this.id = id;
  }

}
