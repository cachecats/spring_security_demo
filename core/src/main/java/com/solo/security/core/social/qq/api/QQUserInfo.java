package com.solo.security.core.social.qq.api;

import lombok.Data;

/**
 * QQ返回的用户信息，需要参考QQ的文档定义.
 *
 * @Author: solo
 * @Date: 2019/10/21 10:12 PM
 * @Version 1.0
 */
@Data
public class QQUserInfo {

  private Integer ret;
  private String msg;
  private Integer is_lost;
  private String nickname;
  private String gender;
  private Integer gender_type;
  private String figureurl;
  private String figureurl_1;
  private String figureurl_2;
  private String figureurl_qq_1;
  private String figureurl_qq_2;
  private String figureurl_qq;
  private String figureurl_type;
  private String is_yellow_vip;
  private String vip;
  private String yellow_vip_level;
  private String level;
  private String is_yellow_year_vip;
  private String openId;
  private String province;
  private String city;
  private String year;
  private String constellation;
}
