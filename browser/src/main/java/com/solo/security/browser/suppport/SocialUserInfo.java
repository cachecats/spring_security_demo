package com.solo.security.browser.suppport;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/22 10:47 PM
 * @Version 1.0
 */
@Data
public class SocialUserInfo {

  private String providerId;
  private String providerUserId;
  private String nickname;
  private String headImg;
}
