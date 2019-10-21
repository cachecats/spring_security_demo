package com.solo.security.core.authentication;

import com.solo.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Author: solo
 * @Date: 2019/10/21 10:55 AM
 * @Version 1.0
 */
public abstract class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AuthenticationSuccessHandler myAuthenticationSuccessHandler;
  @Autowired
  AuthenticationFailureHandler myAuthenticationFailureHandler;

  protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
        .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
        .successHandler(myAuthenticationSuccessHandler)
        .failureHandler(myAuthenticationFailureHandler);
  }

}
