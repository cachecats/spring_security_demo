package com.solo.security.async;

import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Author: solo
 * @Date: 2019/10/10 11:01 PM
 * @Version 1.0
 */
@RestController
@Slf4j
public class AsyncController {

  @Autowired
  MockQueue mockQueue;
  @Autowired
  DeferredResultHolder deferredResultHolder;

  @GetMapping("/order")
  public Callable<String> getOrder() throws InterruptedException {
    log.info("主线程开始");

    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        log.info("副线程开始");
        Thread.sleep(1000);
        log.info("副线程结束");
        return "get order success";
      }
    };

    log.info("主线程结束");
    return callable;
  }

  @GetMapping("/order2")
  public DeferredResult<String> getOrder2() {
    log.info("主线程开始");

    //生成一个8位的随机数
    String orderId = RandomStringUtils.randomAlphanumeric(8);
    mockQueue.setPlaceOrder(orderId);
    DeferredResult<String> result = new DeferredResult<>();
    deferredResultHolder.getMap().put(orderId, result);
    log.info("主线程结束");

    return result;
  }


}
