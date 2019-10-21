package com.solo.security.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.solo.security.validator.MyConstraint;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/9/24 11:20 PM
 * @Version 1.0
 */
@Data
public class User {

  //定义两个视图
  public interface UserSimpleView {};
  public interface UserDetailView extends UserSimpleView{};

  @MyConstraint(message = "I am MyConstraint message")
  private String username;
  @NotBlank(message = "密码不能为空")
  private String password;
  private Integer id;
  private Date birthday;

  //指定视图
  @JsonView(UserSimpleView.class)
  public String getUsername() {
    return username;
  }

  //指定视图
  @JsonView(UserDetailView.class)
  public String getPassword() {
    return password;
  }

  @JsonView(UserSimpleView.class)
  public Integer getId() {
    return id;
  }

  @JsonView(UserSimpleView.class)
  public Date getBirthday() {
    return birthday;
  }
}
