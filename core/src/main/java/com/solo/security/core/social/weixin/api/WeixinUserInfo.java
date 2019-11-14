package com.solo.security.core.social.weixin.api;

import lombok.Data;

/**
 * 微信获取用户信息返回的字段
 * @Author: solo
 * @Date: 2019/10/23 9:56 PM
 * @Version 1.0
 */
@Data
public class WeixinUserInfo {

  private String openid;
  private String nickname;
  private String sex;
  private String province;
  private String city;
  private String country;
  private String headimgurl;
  private String privilege;
  private String unionid;
}
