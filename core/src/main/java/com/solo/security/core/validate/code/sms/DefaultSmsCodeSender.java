package com.solo.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信验证码发送逻辑
 * @Author: solo
 * @Date: 2019/10/20 1:58 PM
 * @Version 1.0
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

  @Override
  public void send(String phone, String code) {
    log.info("向手机 {} 发送验证码：{}", phone, code);
  }
}
