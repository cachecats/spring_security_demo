package com.solo.security.controller;

import java.util.Arrays;

/**
 * @Author: solo
 * @Date: 2019/10/10 9:06 PM
 * @Version 1.0
 */
public class Test {

  @org.junit.Test
  public void add(){
    int[] nums = {1,4,6,11,9};
    int target = 20;
    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if ((nums[i] + nums[j]) == target){
          System.out.println(nums[i] + ", " + nums[j]);
        }
      }
    }
  }
}
