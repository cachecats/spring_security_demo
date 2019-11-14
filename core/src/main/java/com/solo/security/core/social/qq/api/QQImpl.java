package com.solo.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @Author: solo
 * @Date: 2019/10/21 10:14 PM
 * @Version 1.0
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

  private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
  private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

  private String appId;
  private String openId;

  private ObjectMapper objectMapper = new ObjectMapper();


  public QQImpl(String accessToken, String appId){
    super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    this.appId = appId;
    String url = String.format(URL_GET_OPENID, accessToken);
    //请求url获取 openid
    String result = getRestTemplate().getForObject(url, String.class);
    log.info("result: {}", result);
    this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    log.info("openId: {}", openId);
  }

  @Override
  public QQUserInfo getUserInfo() {
    String url = String.format(URL_GET_USERINFO, this.appId, this.openId);
    String result = getRestTemplate().getForObject(url, String.class);
    log.info("获取用户信息：{}", result);
    try {
      QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
      qqUserInfo.setOpenId(openId);
      log.info("qqUserInfo：{}", qqUserInfo);
      return qqUserInfo;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("获取用户信息失败", e);
    }
  }
}
