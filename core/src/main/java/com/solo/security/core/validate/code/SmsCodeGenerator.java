package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityProperties;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/20 1:49 PM
 * @Version 1.0
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

  @Autowired
  SecurityProperties securityProperties;

  @Override
  public ValidateCode createCode(HttpServletRequest request) {
    String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
    return new ValidateCode(code, securityProperties.getCode().getSms().getExpiredIn());
  }
}
