package com.solo.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 设置个性化的 filterProcessesUrl
 * @Author: solo
 * @Date: 2019/10/22 4:23 PM
 * @Version 1.0
 */
public class SoloSpringSocialConfigurer extends SpringSocialConfigurer {

  private String filterProcessesUrl;

  public SoloSpringSocialConfigurer(String filterProcessesUrl) {
    this.filterProcessesUrl = filterProcessesUrl;
  }

  @Override
  protected <T> T postProcess(T object) {
    SocialAuthenticationFilter filter  = (SocialAuthenticationFilter)super.postProcess(object);
    filter.setFilterProcessesUrl(filterProcessesUrl);
    return (T)filter;
  }
}
