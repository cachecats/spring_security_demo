package com.solo.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: solo
 * @Date: 2019/10/15 10:31 PM
 * @Version 1.0
 */
public class ValidateCodeException extends AuthenticationException {

  private static final long serialVersionUID = 7627677213697011648L;

  public ValidateCodeException(String msg) {
    super(msg);
  }
}
