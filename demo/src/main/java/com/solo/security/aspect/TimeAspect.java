package com.solo.security.aspect;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/10 5:21 PM
 * @Version 1.0
 */
//@Aspect
//@Component
@Slf4j
public class TimeAspect {

  @Around("execution(* UserController.*(..))")
  public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
    log.info("time aspect");
    long start = new Date().getTime();
    Object proceed = pjp.proceed();
    log.info("time aspect 时长：{}", (new Date().getTime() - start));
    Object[] args = pjp.getArgs();
    for (Object obj : args) {
      log.info("参数: {}", obj);
    }
    return proceed;
  }
}
