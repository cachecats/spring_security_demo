package com.solo.security.controller;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author: solo
 * @Date: 2019/9/24 11:01 PM
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {

  @Autowired
  WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void whenQuerySuccess() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
        .param("username", "solo")
        .param("age", "20")
        .param("addr", "tianjin")
        .param("page", "5")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
        .andReturn().getResponse().getContentAsString();
    log.info(result);
  }

  @Test
  public void whenGetDetail() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
        .andReturn().getResponse().getContentAsString();
    log.info(result);
  }

  @Test
  public void whenGetDetailFail() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  public void whenCreateUser() throws Exception {
    String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+ new Date().getTime() +"}";
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andReturn().getResponse().getContentAsString();
    log.info(result);
  }

  @Test
  public void whenUpdateUser() throws Exception{
    String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+ new Date().getTime() +"}";
    String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andReturn().getResponse().getContentAsString();
    log.info(result);
  }

  @Test
  public void whenDeleteSuccess() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void whenUploadSuccess() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
        .file(new MockMultipartFile("file", "test.txt", "multipart/form-data",
            "hello world".getBytes())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn().getResponse().getContentAsString();
    log.info("result: {}", result);
  }
}