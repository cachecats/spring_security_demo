package com.solo.security.core.properties;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/12 5:57 PM
 * @Version 1.0
 */
@Data
public class BrowserProperties {

  //登录页面。给定一个默认值，如果用户没有单独配置，则用默认值
  private String loginPage = "/login.html";
  //登录类型。默认json
  private LoginType loginType = LoginType.JSON;
  //记住我过期时间
  private int rememberMeSeconds = 3600;
  //注册页面
  private String signUpUrl = "/signUp.html";
}
