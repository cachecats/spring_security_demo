package com.solo.security.core.social.weixin.connect;

import com.solo.security.core.social.weixin.api.Weixin;
import com.solo.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 *
 * @Author: solo
 * @Date: 2019/11/9 5:47 PM
 * @Version 1.0
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

  private String openId;


  public WeixinAdapter() {
  }

  public WeixinAdapter(String openId) {
    this.openId = openId;
  }

  @Override
  public boolean test(Weixin weixin) {
    return true;
  }

  @Override
  public void setConnectionValues(Weixin weixin, ConnectionValues connectionValues) {
    WeixinUserInfo userInfo = weixin.getUserInfo(openId);
    connectionValues.setProviderUserId(userInfo.getOpenid());
    connectionValues.setProfileUrl(userInfo.getHeadimgurl());
    connectionValues.setDisplayName(userInfo.getNickname());
  }

  @Override
  public UserProfile fetchUserProfile(Weixin weixin) {
    return null;
  }

  @Override
  public void updateStatus(Weixin weixin, String s) {

  }
}
