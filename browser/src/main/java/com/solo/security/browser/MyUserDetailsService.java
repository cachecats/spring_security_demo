package com.solo.security.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/12 11:17 AM
 * @Version 1.0
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("登录的用户名：{}", username);
    //根据用户名查询用户信息。这步应该查数据库，这里省略
    //将密码加密。注意在正常项目中加密密码应该在用户注册时做，直接从数据库里读出密码给 password 赋值即可。
    String password = passwordEncoder.encode("123456");
    log.info("登录的加密密码：{}", password);
    return new User(username, password, true, true, true, true,
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }
}
