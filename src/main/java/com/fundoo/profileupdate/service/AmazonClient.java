package com.fundoo.profileupdate.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface AmazonClient {
    String uploadFile(MultipartFile multipartFile, String email) ;

    ByteArrayOutputStream downloadFile(String keyname);
}
