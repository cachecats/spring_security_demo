package com.solo.security.core.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @Author: solo
 * @Date: 2019/11/9 4:24 PM
 * @Version 1.0
 */
@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

  private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
  private String accessToken;

  private ObjectMapper objectMapper = new ObjectMapper();

  public WeixinImpl(String accessToken) {
    super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    this.accessToken = accessToken;
  }

  /**
   * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖了原来的方法。
   */
  @Override
  protected List<HttpMessageConverter<?>> getMessageConverters() {
    List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
    messageConverters.remove(0);
    messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    return messageConverters;
  }

  @Override
  public WeixinUserInfo getUserInfo(String openId) {
    String url = String.format(URL_GET_USER_INFO, accessToken, openId);
    String response = getRestTemplate().getForObject(url, String.class);
    //判断返回结果中是否包含错误信息。微信的错误返回格式：{"errcode":40003,"errmsg":" invalid openid "}
    if (StringUtils.contains(response, "errcode")){
      log.error("获取微信userinfo失败：{}", response);
      return null;
    }
    log.info("获取微信userinfo成功:{}", response);
    WeixinUserInfo weixinUserInfo = null;
    try {
      //将 json 格式化为对象
      weixinUserInfo = objectMapper.readValue(response, WeixinUserInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return weixinUserInfo;
  }
}
