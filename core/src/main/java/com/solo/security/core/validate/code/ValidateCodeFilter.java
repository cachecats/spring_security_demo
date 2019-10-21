package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityConstants;
import com.solo.security.core.properties.SecurityProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Author: solo
 * @Date: 2019/10/15 10:26 PM
 * @Version 1.0
 */
@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;
  @Autowired
  private SecurityProperties securityProperties;
  @Autowired
  private ValidateCodeProcessorHolder processorHolder;
  // 存放所有需要校验验证码的url
  private Set<String> urls = new HashSet<>();
  //存放所有需要校验验证码的url
  private Map<String, ValidateCodeType> urlMap = new HashMap<>();
  //校验路径的工具类
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  /**
   * 初始化 urls
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
    addUrlToMap(securityProperties.getCode().getImage().getUrls(), ValidateCodeType.IMAGE);

    urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
    addUrlToMap(securityProperties.getCode().getSms().getUrls(), ValidateCodeType.SMS);
  }

  /**
   * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
   */
  protected void addUrlToMap(String urlString, ValidateCodeType type) {
    if (StringUtils.isNotBlank(urlString)) {
      String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
      for (String url : urls) {
        urlMap.put(url, type);
      }
    }
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    ValidateCodeType type = getValidateCodeType(request);
    if (type != null) {
      logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
      try {
        processorHolder.findValidateCodeProcessor(type)
            .validate(new ServletWebRequest(request, response));
        logger.info("验证码校验通过");
      } catch (ValidateCodeException exception) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }
    //执行下一个过滤器
    filterChain.doFilter(request, response);
  }

  /**
   * 获取校验码的类型，如果当前请求不需要校验，则返回null
   */
  private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
    ValidateCodeType result = null;
    if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
      Set<String> urls = urlMap.keySet();
      for (String url : urls) {
        if (antPathMatcher.match(url, request.getRequestURI())) {
          result = urlMap.get(url);
        }
      }
    }
    return result;
  }
}
