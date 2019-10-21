package com.solo.security.interceptor;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: solo
 * @Date: 2019/10/10 3:00 PM
 * @Version 1.0
 */
@Component
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    log.info("-----preHandle");
    request.setAttribute("start", new Date().getTime());

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    log.info("处理的类名：{}", handlerMethod.getBean().getClass().getName());
    log.info("处理的方法名：{}", handlerMethod.getMethod().getName());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) {
    log.info("-----postHandle");
    long start = (long) request.getAttribute("start");
    log.info("-----执行时间：" + (new Date().getTime() - start));
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    log.info("-----afterCompletion");
    log.info("-----Exception is: " + ex);

  }
}
