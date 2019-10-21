package com.solo.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: solo
 * @Date: 2019/10/12 5:57 PM
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "solo.security")
@Data
public class SecurityProperties {

  private BrowserProperties browser = new BrowserProperties();
  private ValidateCodeProperties code = new ValidateCodeProperties();

}
