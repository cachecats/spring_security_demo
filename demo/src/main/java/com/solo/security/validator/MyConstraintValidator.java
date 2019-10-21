package com.solo.security.validator;

import com.solo.security.service.HelloService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 在这个类中可以使用 @AutoWired 来注入其他的服务
 * @Author: solo
 * @Date: 2019/9/25 5:36 PM
 * @Version 1.0
 */
@Slf4j
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

  @Autowired
  HelloService helloService;

  @Override
  public void initialize(MyConstraint constraintAnnotation) {
    log.info("MyConstraintValidator initialize...");
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
    log.info("isValid value: {}", value);
    helloService.greeting((String)value);
    return true;
  }
}
