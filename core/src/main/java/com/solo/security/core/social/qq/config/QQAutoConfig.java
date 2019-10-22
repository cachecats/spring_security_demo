package com.solo.security.core.social.qq.config;

import com.solo.security.core.properties.QQProperties;
import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @Author: solo
 * @Date: 2019/10/22 2:22 PM
 * @Version 1.0
 *
 * SocialConfigurerAdapter
 */
@Configuration
@ConditionalOnProperty(prefix = "solo.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;

  public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
      Environment environment) {
    configurer.addConnectionFactory(this.createConnectionFactory());
  }

  public ConnectionFactory<?> createConnectionFactory() {
    QQProperties qqProperties = securityProperties.getSocial().getQq();
    return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(),
        qqProperties.getAppSecret());
  }

}
