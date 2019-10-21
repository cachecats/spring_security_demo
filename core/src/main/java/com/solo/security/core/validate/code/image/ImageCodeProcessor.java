package com.solo.security.core.validate.code.image;

import com.solo.security.core.validate.code.image.ImageCode;
import com.solo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/20 3:25 PM
 * @Version 1.0
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

  @Override
  protected void send(ServletWebRequest request, ImageCode validateCode) throws IOException {
    ImageIO.write(validateCode.getImage(), "PNG", request.getResponse().getOutputStream());
  }

}
