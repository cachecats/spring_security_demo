package com.solo.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成验证码的接口.
 *
 * @Author: solo
 * @Date: 2019/10/17 10:32 PM
 * @Version 1.0
 */
public interface ValidateCodeGenerator {

  ValidateCode generate(ServletWebRequest request);

}
