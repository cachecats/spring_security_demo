package com.solo.security.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/22 11:11 PM
 * @Version 1.0
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

  @Override
  public String execute(Connection<?> connection) {
    //根据社交用户信息默认创建用户并返回用户唯一标识
    //TODO 真实逻辑请根据需求生成用户唯一标识
    return connection.getDisplayName();
  }
}
