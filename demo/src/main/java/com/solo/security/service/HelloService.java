package com.solo.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: solo
 * @Date: 2019/9/25 5:40 PM
 * @Version 1.0
 */
@Slf4j
@Service
public class HelloService {

  public void greeting(String name){
    log.info("{} greeting", name);
  }
}
