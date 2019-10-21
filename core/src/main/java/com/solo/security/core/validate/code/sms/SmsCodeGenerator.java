package com.solo.security.core.validate.code.sms;

import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.validate.code.ValidateCode;
import com.solo.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/20 1:49 PM
 * @Version 1.0
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public ValidateCode generate(ServletWebRequest request) {
    String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
    return new ValidateCode(code, securityProperties.getCode().getSms().getExpiredIn());
  }

  public SecurityProperties getSecurityProperties() {
    return securityProperties;
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }

}
