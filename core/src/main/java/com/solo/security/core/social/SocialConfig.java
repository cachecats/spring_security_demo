package com.solo.security.core.social;

import com.solo.security.core.properties.SecurityProperties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.util.Assert;

/**
 * @Author: solo
 * @Date: 2019/10/22 11:26 AM
 * @Version 1.0
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

  @Autowired
  private DataSource dataSource;
  @Autowired
  private SecurityProperties securityProperties;
  @Autowired(required = false)
  ConnectionSignUp connectionSignUp;

  @Override
  public UsersConnectionRepository getUsersConnectionRepository(
      ConnectionFactoryLocator connectionFactoryLocator) {
    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
        dataSource, connectionFactoryLocator, Encryptors.noOpText());
    //设置数据库表名的前缀，因为表名为 solo_UserConnection
    repository.setTablePrefix("solo_");
    //如果这个用户没有在系统中注册过，则默认在系统中注册一个账号
    if (connectionSignUp != null){
      repository.setConnectionSignUp(connectionSignUp);
    }
    return repository;
  }

  @Override
  public UserIdSource getUserIdSource() {
    return new SecurityContextUserIdSource();
  }

  @Bean
  public SpringSocialConfigurer soloSocialSecurityConfig() {
    String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
    SoloSpringSocialConfigurer socialConfigurer = new SoloSpringSocialConfigurer(
        filterProcessesUrl);
    //配置注册页面的地址
    socialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
    return socialConfigurer;
  }

  @Bean
  public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator){
    return new ProviderSignInUtils(locator, getUsersConnectionRepository(locator));
  }

  private static class SecurityContextUserIdSource implements UserIdSource {

    private SecurityContextUserIdSource() {
    }

    public String getUserId() {
      SecurityContext context = SecurityContextHolder.getContext();
      Authentication authentication = context.getAuthentication();
      Assert
          .state(authentication != null, "Unable to get a ConnectionRepository: no user signed in");
      return authentication.getName();
    }
  }
}
