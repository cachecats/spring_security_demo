package com.solo.security.core.properties;

/**
 * @Author: solo
 * @Date: 2019/10/20 3:22 PM
 * @Version 1.0
 */
public interface SecurityConstants {

  /**
   * 当请求需要身份认证时，默认跳转的url
   */
  String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

  /**
   *  默认的用户名密码登录请求处理url
   */
  String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

  /**
   * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
   */
  public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
  /**
   * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
   */
  public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
}
