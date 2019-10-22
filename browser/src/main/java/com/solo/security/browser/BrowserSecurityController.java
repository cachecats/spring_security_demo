package com.solo.security.browser;

import com.solo.security.browser.suppport.SimpleResponse;
import com.solo.security.browser.suppport.SocialUserInfo;
import com.solo.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/12 5:40 PM
 * @Version 1.0
 */
@RestController
@Slf4j
public class BrowserSecurityController {

  private RequestCache requestCache = new HttpSessionRequestCache();
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  SecurityProperties securityProperties;
  @Autowired
  ProviderSignInUtils providerSignInUtils;

  @RequestMapping("/authentication/require")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public SimpleResponse requireAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    //判断如果是 html 请求，则跳转到登录页。如果不是 html 请求，则返回 401 和错误信息
    if (savedRequest != null) {
      String redirectUrl = savedRequest.getRedirectUrl();
      log.info("引发跳转的url：{}", redirectUrl);
      if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
        //通过securityProperties.getBrowser().getLoginPage()拿到用户配置的地址
        redirectStrategy
            .sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
      }
    }
    return new SimpleResponse("访问的服务需要身份认证，请引导至登录页");
  }

  @GetMapping("/social/user")
  public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
    SocialUserInfo socialUserInfo = new SocialUserInfo();
    Connection<?> connection = providerSignInUtils
        .getConnectionFromSession(new ServletWebRequest(request));
    socialUserInfo.setNickname(connection.getDisplayName());
    socialUserInfo.setProviderId(connection.getKey().getProviderId());
    socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
    socialUserInfo.setHeadImg(connection.getImageUrl());
    return socialUserInfo;
  }
}
