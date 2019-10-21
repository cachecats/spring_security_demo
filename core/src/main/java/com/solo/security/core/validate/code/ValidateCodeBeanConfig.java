package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.solo.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: solo
 * @Date: 2019/10/17 10:46 PM
 * @Version 1.0
 */
@Configuration
public class ValidateCodeBeanConfig {

  @Autowired
  SecurityProperties securityProperties;

  /**
   * 在系统中没有找到 imageCodeGenerator 才会调用这个方法.
   */
  @Bean
  @ConditionalOnMissingBean(name = "imageCodeGenerator")
  public ValidateCodeGenerator imageCodeGenerator() {
    ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
    imageCodeGenerator.setSecurityProperties(securityProperties);
    return imageCodeGenerator;
  }

  /**
   * @ConditionalOnMissingBean 的参数使用 name 和接口名效果一样
   */
  @Bean
  @ConditionalOnMissingBean(SmsCodeSender.class)
  public SmsCodeSender smsCodeSender(){
    return new DefaultSmsCodeSender();
  }
}
