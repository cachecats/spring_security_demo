package com.solo.security.core.social.qq.connect;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: solo
 * @Date: 2019/10/22 5:26 PM
 * @Version 1.0
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

  public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl,
      String accessTokenUrl) {
    super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    //把这个值设为 true，才会在发请求时带上 client_id 和 client_secret
    setUseParametersForClientAuthentication(true);
  }

  @Override
  protected RestTemplate createRestTemplate() {
    RestTemplate restTemplate = super.createRestTemplate();
    //添加一个 StringHttpMessageConverter
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    return restTemplate;
  }

  @Override
  protected AccessGrant postForAccessGrant(String accessTokenUrl,
      MultiValueMap<String, String> parameters) {
    //发送请求获取 accessToken
    String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
    log.info("获取 accessToken 的响应：{}", responseStr);
    //从响应中截取三个参数
    String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
    String accessToken = StringUtils.substringAfter(items[0], "=");
    Long expiresIn = new Long(StringUtils.substringAfter(items[1], "="));
    String refreshToken = StringUtils.substringAfter(items[2], "=");
    return new AccessGrant(accessToken, null, refreshToken, expiresIn);
  }
}
