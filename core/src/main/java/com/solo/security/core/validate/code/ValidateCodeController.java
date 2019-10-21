package com.solo.security.core.validate.code;

import com.solo.security.core.properties.SecurityConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  ValidateCodeProcessorHolder processorHolder;

  @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
  public void createCOde(HttpServletRequest request, HttpServletResponse response,
      @PathVariable String type)
      throws Exception {
    processorHolder.findValidateCodeProcessor(type)
        .create(new ServletWebRequest(request, response));
  }

}
