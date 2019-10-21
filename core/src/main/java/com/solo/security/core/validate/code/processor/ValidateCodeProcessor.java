package com.solo.security.core.validate.code.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/20 3:05 PM
 * @Version 1.0
 */
public interface ValidateCodeProcessor {

  /**
   * 验证码放入session时的前缀
   */
  String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

  /**
   * 创建验证码
   * @param request
   * @throws Exception
   */
  void create(ServletWebRequest request) throws Exception;
}
