package com.solo.security.core.properties;

import lombok.Data;

/**
 * 验证码配置文件，包含图片验证码和短信验证码
 * @Author: solo
 * @Date: 2019/10/17 5:20 PM
 * @Version 1.0
 */
@Data
public class ValidateCodeProperties {

  private ImageCodeProperties image = new ImageCodeProperties();
  private SmsCodeProperties sms = new SmsCodeProperties();

}
