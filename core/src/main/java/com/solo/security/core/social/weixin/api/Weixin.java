package com.solo.security.core.social.weixin.api;

/**
 * @Author: solo
 * @Date: 2019/10/23 9:56 PM
 * @Version 1.0
 */
public interface Weixin {

  /**
   * 获取微信的用户信息。注意在获取 accessToken 时同时获取到了 openId.
   */
  WeixinUserInfo getUserInfo(String openId);
}
