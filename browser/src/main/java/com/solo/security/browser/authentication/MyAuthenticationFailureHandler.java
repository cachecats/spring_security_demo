package com.solo.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.security.browser.suppport.SimpleResponse;
import com.solo.security.core.properties.LoginType;
import com.solo.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/13 8:07 PM
 * @Version 1.0
 */
@Slf4j
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    log.info("登录失败");

    //如果配置的是 json 格式则执行自定义逻辑，否则执行默认逻辑。
    if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)){
      httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
    }else{
      super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }

  }
}
