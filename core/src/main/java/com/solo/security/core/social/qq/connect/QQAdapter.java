package com.solo.security.core.social.qq.connect;

import com.solo.security.core.social.qq.api.QQ;
import com.solo.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Author: solo
 * @Date: 2019/10/22 10:17 AM
 * @Version 1.0
 */
public class QQAdapter implements ApiAdapter<QQ> {

  @Override
  public boolean test(QQ qq) {
    return true;
  }

  @Override
  public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
    QQUserInfo userInfo = qq.getUserInfo();
    connectionValues.setDisplayName(userInfo.getNickname());
    connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
    connectionValues.setProfileUrl(null);
    connectionValues.setProviderUserId(userInfo.getOpenId());
  }

  @Override
  public UserProfile fetchUserProfile(QQ qq) {
    return null;
  }

  @Override
  public void updateStatus(QQ qq, String s) {
    //QQ不需要更新状态
  }
}
