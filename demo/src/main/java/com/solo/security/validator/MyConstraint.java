package com.solo.security.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义注解
 * MyConstraint 中必须包含 message()，groups()，payload() 三个方法
 * @Author: solo
 * @Date: 2019/9/25 5:31 PM
 * @Version 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {

  String message() default "{MyConstraint default message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
