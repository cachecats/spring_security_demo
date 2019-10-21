package com.solo.security.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列
 *
 * @Author: solo
 * @Date: 2019/10/10 11:35 PM
 * @Version 1.0
 */
@Slf4j
@Component
public class MockQueue {

  private String placeOrder;
  private String completeOrder;

  /**
   * set 值时模拟下单处理，耗时1秒。处理完后把 placeOrder 赋值给 completeOrder。 假设 completeOrder
   * 被赋值即订单处理完成。监听器是根据这个来判断是否完成的。
   */
  public void setPlaceOrder(String placeOrder) {
    this.placeOrder = placeOrder;
    //模拟一个线程处理
    new Thread(() -> {
      log.info("接到下单请求: {}", placeOrder);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.completeOrder = placeOrder;
      log.info("下单请求处理完毕：{}", placeOrder);
    }).start();

  }

  public void setCompleteOrder(String completeOrder) {
    this.completeOrder = completeOrder;
  }

  public String getPlaceOrder() {
    return placeOrder;
  }

  public String getCompleteOrder() {
    return completeOrder;
  }
}
