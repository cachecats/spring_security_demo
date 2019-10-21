package com.solo.security.core.validate.code;

import com.solo.security.core.validate.code.processor.ValidateCodeProcessor;
import com.solo.security.core.validate.code.sms.SmsCodeSender;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.bytebuddy.asm.Advice.Unused;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: solo
 * @Date: 2019/10/15 11:07 AM
 * @Version 1.0
 */
@RestController
@CrossOrigin
public class ValidateCodeController {

//  public static final String SESSION_KEY = "session_key";

  @Autowired
  private Map<String, ValidateCodeProcessor> validateCodeProcessors;

  @GetMapping("/code/{type}")
  public void createCOde(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
      throws Exception {
    validateCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
  }

}
