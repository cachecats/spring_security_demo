package com.solo.security.core.properties;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/22 2:20 PM
 * @Version 1.0
 */
@Data
public class SocialProperties {

  private QQProperties qq = new QQProperties();
  private WeixinProperties weixin = new WeixinProperties();
  //第三方登录默认的前缀 url
  private String filterProcessesUrl = "/auth";
}
