//package com.fundoo.controller;
//
//import com.fundoo.dto.LoginDto;
//import com.fundoo.dto.ResponseDto;
//import com.fundoo.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/fundoo")
//public class LoginController {
//
//    @Autowired
//    LoginService loginService;
//
//    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseDto login(@RequestBody LoginDto loginDto) {
//        return loginService.login(loginDto);
//    }
//}
