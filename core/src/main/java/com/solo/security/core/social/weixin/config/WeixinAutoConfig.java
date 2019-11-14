package com.solo.security.core.social.weixin.config;

import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.properties.WeixinProperties;
import com.solo.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @Author: solo
 * @Date: 2019/11/13 9:02 PM
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "solo.security.social.weixin", name = "app-id")
public class WeixinAutoConfig extends SocialConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;


  public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
      Environment environment) {
    configurer.addConnectionFactory(this.createConnectionFactory());
  }

  public ConnectionFactory<?> createConnectionFactory() {
    WeixinProperties properties = securityProperties.getSocial().getWeixin();
    return new WeixinConnectionFactory(properties.getProviderId(), properties.getAppId(),
        properties.getAppSecret());
  }

}
