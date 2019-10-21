package com.solo.security.core.properties;

import lombok.Data;

/**
 * 验证码的配置类
 * @Author: solo
 * @Date: 2019/10/17 5:17 PM
 * @Version 1.0
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{

  /**
   * 由于继承了SmsCodeProperties，sms验证码默认是6位，而image验证码默认是4位
   * 所以在 ImageCodeProperties 的构造方法中将 length 的长度设为4位
   */
  public ImageCodeProperties(){
    setLength(4);
  }

  //验证码图片的宽度
  private int width = 120;
  //验证码图片的高度
  private int height = 40;

}
