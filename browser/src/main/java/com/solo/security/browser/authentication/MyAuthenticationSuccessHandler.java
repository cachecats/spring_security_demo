package com.solo.security.browser.authentication;

import com.solo.security.core.properties.LoginType;
import com.solo.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/13 7:53 PM
 * @Version 1.0
 */
@Slf4j
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication)
      throws IOException, ServletException {
    log.info("登录成功");
    //如果配置的是 json 格式则执行自定义逻辑，否则执行默认逻辑。
    if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
    }else {
      super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
  }
}
