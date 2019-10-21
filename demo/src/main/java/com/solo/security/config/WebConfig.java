package com.solo.security.config;

import com.solo.security.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: solo
 * @Date: 2019/10/10 2:57 PM
 * @Version 1.0
 */
//@Configuration
//public class WebConfig extends WebMvcConfigurationSupport {
//
//  @Autowired
//  TimeInterceptor timeInterceptor;
//
////  @Bean
////  public FilterRegistrationBean timeFilter(){
////    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
////    TimeFilter timeFilter = new TimeFilter();
////    registrationBean.setFilter(timeFilter);
////    List<String> urls = new ArrayList<>();
////    urls.add("/*");
////    registrationBean.setUrlPatterns(urls);
////    return registrationBean;
////  }
//
//}
