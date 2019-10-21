package com.solo.security.core.validate.code.image;

import com.solo.security.core.validate.code.ValidateCode;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @Author: solo
 * @Date: 2019/10/15 10:55 AM
 * @Version 1.0
 */
@Data
public class ImageCode extends ValidateCode {

  private BufferedImage image;

  public ImageCode(BufferedImage image, String code, int expireIn) {
    super(code, expireIn);
    this.image = image;
  }

  public ImageCode(BufferedImage image, String code, LocalDateTime expiredTime) {
    super(code, expiredTime);
    this.image = image;
  }

}
