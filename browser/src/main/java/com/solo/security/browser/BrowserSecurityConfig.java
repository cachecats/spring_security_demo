package com.solo.security.browser;

import com.solo.security.core.authentication.AbstractChannelSecurityConfig;
import com.solo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.solo.security.core.properties.SecurityConstants;
import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.validate.code.ValidateCodeSecurityConfig;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

/**
 * @Author: solo
 * @Date: 2019/10/11 6:32 PM
 * @Version 1.0
 */
//@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

  @Autowired
  SecurityProperties securityProperties;
  @Autowired
  AuthenticationSuccessHandler myAuthenticationSuccessHandler;
  @Autowired
  AuthenticationFailureHandler myAuthenticationFailureHandler;
  @Autowired
  DataSource dataSource;
  @Autowired
  UserDetailsService myUserDetailsService;
  @Autowired
  SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
  @Autowired
  ValidateCodeSecurityConfig validateCodeSecurityConfig;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    //第一次启动创建表，以后就不需要了。如果表已经存在还执行这行代码的话会报错
    //jdbcTokenRepository.setCreateTableOnStartup(true);
    jdbcTokenRepository.setDataSource(dataSource);
    return jdbcTokenRepository;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //应用 UsernamePassword 的配置
    applyPasswordAuthenticationConfig(http);

    http
        .apply(validateCodeSecurityConfig)
          .and()
        .apply(smsCodeAuthenticationSecurityConfig)
          .and()
        .rememberMe()
        .tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
        .userDetailsService(myUserDetailsService)
        .and()
        .authorizeRequests()
        //处理跨域请求中的Preflight请求
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
            securityProperties.getBrowser().getLoginPage()).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .cors()
        .and()
        .csrf().disable();

  }

}
