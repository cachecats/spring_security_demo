package com.solo.security.code;

import com.solo.security.core.validate.code.ImageCode;
import com.solo.security.core.validate.code.ValidateCodeGenerator;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/17 10:54 PM
 * @Version 1.0
 */
@Slf4j
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

  @Override
  public ImageCode createCode(HttpServletRequest request) {
    log.info("Demo 中的 createCode");
    return null;
  }
}
