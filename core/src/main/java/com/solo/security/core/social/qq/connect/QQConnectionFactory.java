package com.solo.security.core.social.qq.connect;

import com.solo.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Author: solo
 * @Date: 2019/10/22 10:32 AM
 * @Version 1.0
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

  public QQConnectionFactory(String providerId, String appId, String appSecret) {
    super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
  }
}
