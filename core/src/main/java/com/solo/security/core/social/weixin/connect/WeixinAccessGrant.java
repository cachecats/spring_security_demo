package com.solo.security.core.social.weixin.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 系统提供的 AccessGrant 没有 openId，自定义 WeixinAccessGrant 加上 openId.
 * @Author: solo
 * @Date: 2019/11/9 5:37 PM
 * @Version 1.0
 */
@Data
public class WeixinAccessGrant extends AccessGrant {

  public WeixinAccessGrant() {
    super("");
  }

  public WeixinAccessGrant(String accessToken, String scope, String refreshToken,
      Long expiresIn) {
    super(accessToken, scope, refreshToken, expiresIn);
  }

  private String openId;
}
