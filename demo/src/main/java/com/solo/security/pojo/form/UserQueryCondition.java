package com.solo.security.pojo.form;

import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/9/24 11:35 PM
 * @Version 1.0
 */
@Data
public class UserQueryCondition {

  private String username;
  private Integer age;
  private String addr;

}
