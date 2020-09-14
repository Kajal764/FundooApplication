package com.fundoo.controller;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.dto.UpdatePasswordDto;
import com.fundoo.service.ForgotPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/reset/{token}")
    public String updatePassword(@PathVariable("token") String token) {
        return forgotPWService.redirectToUpatePassword(token);

    }
}

