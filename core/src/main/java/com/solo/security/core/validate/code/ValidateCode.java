package com.solo.security.core.validate.code;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/20 1:12 PM
 * @Version 1.0
 */
@Data
public class ValidateCode {
  private String code;
  private LocalDateTime expiredTime;

  public ValidateCode() {
  }

  public ValidateCode(String code, LocalDateTime expiredTime) {
    this.code = code;
    this.expiredTime = expiredTime;
  }

  public ValidateCode(String code, int expiredIn) {
    this.code = code;
    this.expiredTime = LocalDateTime.now().plusSeconds(expiredIn);
  }

  //判断是否过期
  public boolean isExpired(){
    return LocalDateTime.now().isAfter(expiredTime);
  }
}
