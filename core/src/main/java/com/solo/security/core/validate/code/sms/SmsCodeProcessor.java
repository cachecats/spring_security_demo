package com.solo.security.core.validate.code.sms;

import com.solo.security.core.validate.code.ValidateCode;
import com.solo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import com.solo.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/20 3:32 PM
 * @Version 1.0
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

  @Autowired
  SmsCodeSender smsCodeSender;

  @Override
  protected void send(ServletWebRequest request, ValidateCode validateCode)
      throws ServletRequestBindingException {
    String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
    smsCodeSender.send(mobile, validateCode.getCode());
  }
}
