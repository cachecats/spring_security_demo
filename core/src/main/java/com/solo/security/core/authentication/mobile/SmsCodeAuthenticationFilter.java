package com.solo.security.core.authentication.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * @Author: solo
 * @Date: 2019/10/20 9:56 PM
 * @Version 1.0
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  //请求的电话参数
  public static final String SOLO_SECURITY_FORM_MOBILE_KEY = "mobile";
  private String mobileParameter = SOLO_SECURITY_FORM_MOBILE_KEY;
  private boolean postOnly = true;

  //拦截的路径和方法
  public SmsCodeAuthenticationFilter() {
    super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
  }

  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (this.postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    } else {
      String mobile = this.obtainMobile(request);
      if (mobile == null) {
        mobile = "";
      }
      mobile = mobile.trim();
      SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
      this.setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
  }

  protected String obtainMobile(HttpServletRequest request) {
    return request.getParameter(this.mobileParameter);
  }

  protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }

  public void setUsernameParameter(String mobileParameter) {
    Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
    this.mobileParameter = mobileParameter;
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public final String getMobileParameter() {
    return this.mobileParameter;
  }

}
