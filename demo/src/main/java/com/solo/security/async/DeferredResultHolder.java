package com.solo.security.async;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Author: solo
 * @Date: 2019/10/11 3:21 PM
 * @Version 1.0
 */
@Component
public class DeferredResultHolder {

  private Map<String, DeferredResult<String>> map = new HashMap<>();

  public Map<String, DeferredResult<String>> getMap() {
    return map;
  }

  public void setMap(
      Map<String, DeferredResult<String>> map) {
    this.map = map;
  }
}
