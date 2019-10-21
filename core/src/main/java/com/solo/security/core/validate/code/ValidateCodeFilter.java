package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityProperties;
import com.solo.security.core.validate.code.processor.ValidateCodeProcessor;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Author: solo
 * @Date: 2019/10/15 10:26 PM
 * @Version 1.0
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  //在调用的类上要把 authenticationFailureHandler 和 securityProperties 设置进来
  private AuthenticationFailureHandler authenticationFailureHandler;
  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
  private SecurityProperties securityProperties;
  private Set<String> urls = new HashSet<>();

  //校验路径的工具类
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  private final String IMAGE_SESSION_KEY = ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE";

  /**
   * 初始化 urls
   * @throws ServletException
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    //取出用户设置的 url
    String[] configUrls = StringUtils
        .splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrls(), ",");
    //将用户设置的 url 和登录的接口 /authentication/form 添加到 set 中
    if (configUrls != null){
      urls.addAll(Arrays.asList(configUrls));
    }
    urls.add("/authentication/form");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    //循环判断请求的路径是否需要过滤
    boolean action = false;
    for (String url : urls) {
      if (antPathMatcher.match(url, request.getRequestURI())){
        action = true;
      }
    }

    if (action) {
      //如果验证失败会抛出 ValidateCodeException 异常，捕获这个异常进行错误处理
      try {
        validate(new ServletWebRequest(request));
      } catch (ValidateCodeException exception) {
        log.info("ValidateCodeException: {}", exception);
        //调用自定义的 authenticationFailureHandler 处理异常
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }
    //执行下一个过滤器
    filterChain.doFilter(request, response);
  }

  /**
   * 校验验证码是否正确
   */
  private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
    //生成验证码的时候将 ImageCode 对象存入到了 session 中，这里再取出
    ImageCode imageCode = (ImageCode) sessionStrategy
        .getAttribute(servletWebRequest, IMAGE_SESSION_KEY);
    //获取请求中用户提交的验证码表单
    String codeInRequest = ServletRequestUtils
        .getStringParameter(servletWebRequest.getRequest(), "imageCode");

    log.info("codeInRequest: {}", codeInRequest);
    if (imageCode != null){
      log.info("imageCode: {}", imageCode.getCode());
    }

    if (codeInRequest == null) {
      throw new ValidateCodeException("验证码不存在");
    }

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException("验证码的值不能为空");
    }

    if (imageCode.isExpired()) {
      //将验证码从 session 中移除
      sessionStrategy.removeAttribute(servletWebRequest, IMAGE_SESSION_KEY);
      throw new ValidateCodeException("验证码已过期");
    }

    if (!StringUtils.equalsIgnoreCase(imageCode.getCode(), codeInRequest)) {
      throw new ValidateCodeException("验证码不匹配");
    }

    //将验证码从 session 中移除
    sessionStrategy.removeAttribute(servletWebRequest, IMAGE_SESSION_KEY);
  }


  public void setAuthenticationFailureHandler(
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  public void setSecurityProperties(
      SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
