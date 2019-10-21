package com.solo.security.browser;

import com.solo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.validate.code.SmsCodeFilter;
import com.solo.security.core.validate.code.ValidateCodeFilter;
import javax.sql.DataSource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
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

    //图片验证码过滤器
    ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
    validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
    validateCodeFilter.setSecurityProperties(securityProperties);
    validateCodeFilter.afterPropertiesSet();

    //短信验证码过滤器
    SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
    smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
    smsCodeFilter.setSecurityProperties(securityProperties);
    smsCodeFilter.afterPropertiesSet();

    http
        .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin()
        .loginPage("/authentication/require")
        .loginProcessingUrl("/authentication/form")
        .successHandler(myAuthenticationSuccessHandler)
        .failureHandler(myAuthenticationFailureHandler)
        .and()
        .rememberMe()
        .tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
        .userDetailsService(myUserDetailsService)
        .and()
        .authorizeRequests()
        //处理跨域请求中的Preflight请求
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers("/authentication/require", "/code/*",
            securityProperties.getBrowser().getLoginPage()).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .cors()
        .and()
        .csrf().disable()
        .apply(smsCodeAuthenticationSecurityConfig);

  }

}
