package com.solo.security.controller;

import com.solo.security.pojo.FileInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: solo
 * @Date: 2019/10/10 9:36 PM
 * @Version 1.0
 */
@RestController
@Slf4j
public class FileController {

  String folder = "/Users/solo/Desktop";

  @PostMapping("/file")
  public FileInfo upload(MultipartFile file) throws IOException {
    log.info(file.getName());
    log.info(file.getOriginalFilename());
    log.info(file.getSize()+"");
    File localFile = new File(folder, "test.txt");
    file.transferTo(localFile);
    return new FileInfo(localFile.getAbsolutePath());
  }

  @GetMapping("/file/{id}")
  public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
    try(
        InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
        OutputStream outputStream = response.getOutputStream();
    ) {
      response.setContentType("application/x-download");
      response.addHeader("Content-Disposition", "attachment;filename=test.txt");
      IOUtils.copy(inputStream, outputStream);
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
