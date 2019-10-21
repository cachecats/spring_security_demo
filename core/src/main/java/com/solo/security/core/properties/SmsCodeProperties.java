package com.solo.security.core.properties;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/20 1:06 PM
 * @Version 1.0
 */
@Data
public class SmsCodeProperties {

  //验证码的长度
  private int length = 6;
  //验证码的过期时间
  private int expiredIn = 60;
  //要校验的 uri 路径
  private String urls;

}
