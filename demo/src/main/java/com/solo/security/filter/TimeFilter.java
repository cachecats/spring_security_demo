package com.solo.security.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/9/25 9:43 PM
 * @Version 1.0
 */
@Slf4j
//@Component
public class TimeFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("TimeFilter init------");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    log.info("into doFilter method------");
    long start = new Date().getTime();
    filterChain.doFilter(servletRequest, servletResponse);
    log.info("服务耗时：{}", new Date().getTime() - start);
    log.info("out doFilter method------");
  }

  @Override
  public void destroy() {
    log.info("TimeFilter destroy------");
  }
}
