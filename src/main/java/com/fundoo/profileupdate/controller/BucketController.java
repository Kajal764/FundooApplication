//package com.fundoo.profileupdate.controller;
//
//import com.fundoo.profileupdate.service.AmazonClient;
//import com.fundoo.user.dto.ResponseDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/fundoo/user")
//public class BucketController {
//
//    private AmazonClient amazonClient;
//
//    @Autowired
//    BucketController(AmazonClient amazonClient) {
//        this.amazonClient = amazonClient;
//    }
//
//    @PostMapping("/uploadFile")
//    public ResponseDto uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
//        String file = this.amazonClient.uploadFile(multipartFile);
//        if (file != null)
//            return new ResponseDto("Profile added successfully", 200);
//        return new ResponseDto("Error adding profile", 400);
//    }
//
//}
