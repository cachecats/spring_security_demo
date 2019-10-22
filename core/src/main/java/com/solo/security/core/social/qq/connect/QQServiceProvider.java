package com.solo.security.core.social.qq.connect;

import com.solo.security.core.social.qq.api.QQ;
import com.solo.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @Author: solo
 * @Date: 2019/10/22 9:47 AM
 * @Version 1.0
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

  private String appId;

  private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
  private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

  public QQServiceProvider(String appId, String appSecret) {
    super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    this.appId = appId;
  }

  @Override
  public QQ getApi(String accessToken) {
    return new QQImpl(accessToken, appId);
  }
}
