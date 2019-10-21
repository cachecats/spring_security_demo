package com.solo.security.core.validate.code.sms;

/**
 * @Author: solo
 * @Date: 2019/10/20 1:58 PM
 * @Version 1.0
 */
public interface SmsCodeSender {

  void send(String phone, String code);

}
