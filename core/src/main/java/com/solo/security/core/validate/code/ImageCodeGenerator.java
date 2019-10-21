package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityProperties;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * @Author: solo
 * @Date: 2019/10/17 10:33 PM
 * @Version 1.0
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

  private SecurityProperties securityProperties;

  public void setSecurityProperties(
      SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }

  @Override
  public ImageCode createCode(HttpServletRequest request) {
    //宽高先从请求中拿，请求中没有再从配置文件中拿
    int width = ServletRequestUtils.getIntParameter(request, "width",
        securityProperties.getCode().getImage().getWidth());
    int height = ServletRequestUtils.getIntParameter(request, "height",
        securityProperties.getCode().getImage().getHeight());;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    String code = drawRandomText(width, height, image);
    //过期时间从配置文件中拿
    return new ImageCode(image, code, securityProperties.getCode().getImage().getExpiredIn());
  }

  /**
   * 生成图片验证码
   */
  public String drawRandomText(int width, int height, BufferedImage verifyImg) {

    Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
    graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色
    graphics.fillRect(0, 0, width, height);//填充背景
    graphics.setFont(new Font("微软雅黑", Font.BOLD, 32));

    //数字和字母的组合
    String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    StringBuffer sBuffer = new StringBuffer();
    int x = 20;  //旋转原点的 x 坐标
    String ch = "";
    Random random = new Random();
    //长度从配置文件中拿
    int length = securityProperties.getCode().getImage().getLength();
    for (int i = 0; i < length; i++) {
      graphics.setColor(getRandomColor());
      //设置字体旋转角度
      int degree = random.nextInt() % 30;  //角度小于30度
      int dot = random.nextInt(baseNumLetter.length());
      ch = baseNumLetter.charAt(dot) + "";
      sBuffer.append(ch);
      //正向旋转
      graphics.rotate(degree * Math.PI / 180, x, 45);
      graphics.drawString(ch, x, 30);
      //反向旋转
      graphics.rotate(-degree * Math.PI / 180, x, 45);
      x += 30;
    }

    //画干扰线
    for (int i = 0; i < 2; i++) {
      // 设置随机颜色
      graphics.setColor(getRandomColor());
      // 随机画线
      graphics.drawLine(random.nextInt(width), random.nextInt(height),
          random.nextInt(width), random.nextInt(height));
    }
    //添加噪点
    for (int i = 0; i < 24; i++) {
      int x1 = random.nextInt(width);
      int y1 = random.nextInt(height);
      graphics.setColor(getRandomColor());
      graphics.fillRect(x1, y1, 2, 2);
    }
    return sBuffer.toString();
  }

  /**
   * 随机取色
   */
  private static Color getRandomColor() {
    Random ran = new Random();
    Color color = new Color(ran.nextInt(256),
        ran.nextInt(256), ran.nextInt(256));
    return color;
  }

}
