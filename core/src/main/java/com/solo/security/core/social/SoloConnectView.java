package com.solo.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @Author: solo
 * @Date: 2019/11/14 4:51 PM
 * @Version 1.0
 */
@Slf4j
public class SoloConnectView extends AbstractView {

  @Autowired
  ObjectMapper objectMapper;

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

//    response.setContentType("text/html;charset=UTF-8");
//    log.info("model: {}", model);
//    if (model.get("connections") == null){
//      response.getWriter().write("<h3>解绑成功</h3>");
//    }else {
//      response.getWriter().write("<h3>绑定成功</h3>");
//    }

    Map<String, String> result = new HashMap<>();

    response.setContentType("application/json;charset=UTF-8");
    if (model.get("connections") == null){
      result.put("msg", "解绑成功");
      response.getWriter().write(objectMapper.writeValueAsString(result));
    }else {
      result.put("msg", "绑定成功");
      response.getWriter().write(objectMapper.writeValueAsString(result));
    }
  }
}
