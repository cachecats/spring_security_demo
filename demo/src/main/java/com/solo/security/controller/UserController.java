package com.solo.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.solo.security.pojo.User;
import com.solo.security.pojo.form.UserQueryCondition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/9/24 10:59 PM
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

  @Autowired
  private ProviderSignInUtils providerSignInUtils;

  @PostMapping("/register")
  public void register(User user, HttpServletRequest request){
    //不管是注册用户还是绑定用户，都会拿到用户的唯一标识
    log.info("注册用户");
    //这里用 username 作为用户id，实际项目中根据自己的逻辑生成 userId
    String userId = user.getUsername();
    providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
  }

  @GetMapping("/me")
  public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
    return user;
  }

  @GetMapping
  @JsonView(User.UserSimpleView.class)
  public List<User> query(UserQueryCondition condition,
      @PageableDefault(size = 20, page = 2, sort = "username, asc") Pageable pageable){
    log.info("condition: {}, pageable: {}", condition, pageable);
    List<User> list = new ArrayList<>();
    list.add(new User());
    list.add(new User());
    list.add(new User());

//    throw new UserNotExistException(1);

    return list;
  }

  @GetMapping("/{id:\\d+}")
  @JsonView(User.UserDetailView.class)
  public User getUserDetail(@PathVariable String id){
    User user = new User();
    user.setUsername("tom");
    return user;
  }

  @PostMapping
  @JsonView(User.UserDetailView.class)
  public User create(@Valid @RequestBody User user, BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      String msg = bindingResult.getFieldError().getDefaultMessage();
      log.info("error msg: ${}", msg);
    }
    log.info("user: {}", user);
    user.setId(1);
    return user;
  }

  @PutMapping("/{id:\\d+}")
  public User update(@Valid @RequestBody User user, BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      //循环打印出所有错误
      bindingResult.getAllErrors().stream().forEach(error -> {
        FieldError fieldError = (FieldError) error;
        log.info("field: {}, message: {}", fieldError.getField(), fieldError.getDefaultMessage());
      });
    }
    user.setId(1);
    log.info("user: {}", user);
    return user;
  }

  @DeleteMapping("/{id:\\d+}")
  public void delete(@PathVariable String id){
    log.info("id: {}", id);
  }

}
