package com.solo.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @Author: solo
 * @Date: 2019/11/14 10:16 AM
 * @Version 1.0
 */
@Component("connect/status")
@Slf4j
public class SoloConnectionStatusView extends AbstractView {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

    log.info("connections: {}", connections);

    Map<String, Boolean> result = new HashMap<>();
    for (String key : connections.keySet()) {
      result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
    }

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(result));
  }
}
