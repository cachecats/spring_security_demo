package com.solo.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/20 10:49 PM
 * @Version 1.0
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  AuthenticationSuccessHandler myAuthenticationSuccessHandler;
  @Autowired
  AuthenticationFailureHandler myAuthenticationFailureHandler;
  @Autowired
  UserDetailsService myUserDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
    smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
    smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
    smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

    SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
    provider.setUserDetailsService(myUserDetailsService);

    http.authenticationProvider(provider)
        .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
