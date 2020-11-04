package com.fundoo.profileupdate.controller;

import com.fundoo.profileupdate.service.AmazonClient;
import com.fundoo.user.exception.LoginUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/fundoo/user")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "email") String email) {
        String file = this.amazonClient.uploadFile(multipartFile, email);
        System.out.println(file);
        if (file != null)
            return file;
        throw new LoginUserException("Error while uploading profile");
    }

    @GetMapping("/image/{uploadFileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("uploadFileName") String keyName) {
        System.out.println(keyName);
        ByteArrayOutputStream downloadInputStream = amazonClient.downloadFile(keyName);
        ResponseEntity<byte[]> body = ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + keyName + "\"")
                .body(downloadInputStream.toByteArray());
        return body;
    }
}
