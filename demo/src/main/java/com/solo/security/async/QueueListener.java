package com.solo.security.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: solo
 * @Date: 2019/10/11 3:49 PM
 * @Version 1.0
 */
@Slf4j
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  MockQueue mockQueue;
  @Autowired
  DeferredResultHolder deferredResultHolder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    //新开个线程循环读取 MockQueue 中的completeOrder值，模拟消息队列监听器
    new Thread(() -> {
      while (true){
        if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())){
          String completeOrder = mockQueue.getCompleteOrder();
          log.info("返回订单处理结果：{}", completeOrder);
          //设置处理后返回的值
          deferredResultHolder.getMap().get(completeOrder).setResult("handle order complete");
          mockQueue.setCompleteOrder(null);
        }else{
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }
}
