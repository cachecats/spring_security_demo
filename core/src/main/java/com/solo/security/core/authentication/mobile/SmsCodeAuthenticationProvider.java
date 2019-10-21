package com.solo.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 认证的逻辑
 *
 * @Author: solo
 * @Date: 2019/10/20 10:11 PM
 * @Version 1.0
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService userDetailsService;

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(
      UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * 用 UserDetailsService 获取用户信息，然后重新组装认证过的 AuthenticationToken
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
    UserDetails user = userDetailsService
        .loadUserByUsername((String) authenticationToken.getPrincipal());

    if (user == null) {
      throw new InternalAuthenticationServiceException("无法获取用户信息");
    }

    //new一个SmsCodeAuthenticationToken，用两个参数的构造函数
    SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,
        user.getAuthorities());
    //将 UserDetails 从 authenticationToken 拷贝到 authenticationResult
    authenticationResult.setDetails(authenticationToken.getDetails());
    return authenticationResult;
  }

  /**
   * 判断是 SmsCodeAuthenticationToken 则调用这个 Provider 处理
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
