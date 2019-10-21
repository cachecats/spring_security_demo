package com.solo.security.core.validate.code.impl;

import com.solo.security.core.validate.code.ValidateCode;
import com.solo.security.core.validate.code.ValidateCodeGenerator;
import com.solo.security.core.validate.code.ValidateCodeType;
import com.solo.security.core.validate.code.processor.ValidateCodeProcessor;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/20 3:08 PM
 * @Version 1.0
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements
    ValidateCodeProcessor {

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Autowired
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  @Override
  public void create(ServletWebRequest request) throws Exception {
    C validateCode = generate(request);
    save(request, validateCode);
    send(request, validateCode);
  }

  protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

  private void save(ServletWebRequest request, C validateCode) {
    sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
  }

  private C generate(ServletWebRequest request) {
    String type = getProcessorType(request);
    ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
    return (C) validateCodeGenerator.createCode(request.getRequest());
  }

  /**
   * 构建验证码放入session时的key
   */
  private String getSessionKey(ServletWebRequest request) {
    return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
  }

  /**
   * 根据请求的url获取校验码的类型
   */
  private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
  }

  /**
   * 根据请求的url获取校验码的类型
   */
  private String getProcessorType(ServletWebRequest request) {
    return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
  }
}
