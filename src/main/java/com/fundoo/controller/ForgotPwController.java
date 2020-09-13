package com.fundoo.controller;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.service.ForgotPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/fundoo")
public class ForgotPwController {

    @Autowired
    ForgotPWService forgotPWService;

    @PostMapping(value = "/forgotpassword")
    public ResponseDto forgotPassword(@RequestBody ForgotPwDto forgotPwDto) throws MessagingException {
        return forgotPWService.checkDetails(forgotPwDto);
    }

}

