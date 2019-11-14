package com.solo.security.core.social.weixin.connect;

import com.solo.security.core.social.weixin.api.Weixin;
import com.solo.security.core.social.weixin.api.WeixinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @Author: solo
 * @Date: 2019/11/9 5:52 PM
 * @Version 1.0
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {


  /**
   * 微信获取授权码的url
   */
  private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
  /**
   * 微信获取accessToken的url
   */
  private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";


  public WeixinServiceProvider(String appId, String appSecret) {
    super(new WeixinOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
  }

  @Override
  public Weixin getApi(String accessToken) {
    return new WeixinImpl(accessToken);
  }
}
