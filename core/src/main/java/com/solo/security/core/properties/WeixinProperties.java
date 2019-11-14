package com.solo.security.core.properties;


import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/22 2:11 PM
 * @Version 1.0
 */
@Data
public class WeixinProperties {

  private String appId;
  private String appSecret;
  private String providerId = "weixin";
}
