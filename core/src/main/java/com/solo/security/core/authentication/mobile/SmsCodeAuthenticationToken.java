package com.solo.security.core.authentication.mobile;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用来封装登录信息
 * 参照 UsernamePasswordAuthenticationToken，把 credentials 属性删除即可。credentials 存的是密码，这里不需要。
 *
 * @Author: solo
 * @Date: 2019/10/20 9:48 PM
 * @Version 1.0
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = 510L;
  private final Object principal;

  public SmsCodeAuthenticationToken(String mobile) {
    super((Collection) null);
    this.principal = mobile;
    this.setAuthenticated(false);
  }

  public SmsCodeAuthenticationToken(Object principal,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }

  public Object getCredentials() {
    return null;
  }

  public Object getPrincipal() {
    return this.principal;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    } else {
      super.setAuthenticated(false);
    }
  }

}
