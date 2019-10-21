package com.solo.security.core.validate.code;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/21 2:50 PM
 * @Version 1.0
 */
@Slf4j
@Component
public class ValidateCodeProcessorHolder {

  @Autowired
  private Map<String, ValidateCodeProcessor> validateCodeProcessors;

  public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
    return findValidateCodeProcessor(type.toString().toLowerCase());
  }

  public ValidateCodeProcessor findValidateCodeProcessor(String type){
    String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
    log.info("processor name: {}", name);
    ValidateCodeProcessor processor = validateCodeProcessors.get(name);
    if (processor == null){
      throw new ValidateCodeException("验证码处理器" + name + "不存在");
    }
    return processor;
  }
}
