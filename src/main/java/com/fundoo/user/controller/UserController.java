package com.fundoo.user.controller;

import com.fundoo.user.dto.*;
import com.fundoo.user.service.ForgotPWService;
import com.fundoo.user.service.LoginService;
import com.fundoo.user.service.RegistrationService;
import com.fundoo.user.service.UpdatePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/fundoo")
public class UserController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    LoginService loginService;

    @Autowired
    ForgotPWService forgotPWService;

    @Autowired
    UpdatePasswordService updatePasswordService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws MessagingException, UnsupportedEncodingException {
        return registrationService.register(registerUserDto);
    }

    @GetMapping("/verifyUser")
    public Object validateUser(@RequestParam String token) {
        return registrationService.verifyUser(token);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto login(@RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    @PostMapping(value = "/forgotpassword")
    public ResponseDto forgotPassword(@RequestBody ForgotPwDto forgotPwDto) {
        return forgotPWService.checkDetails(forgotPwDto);
    }

    @GetMapping(value = "/reset/{token}")
    public String updatePassword(@PathVariable("token") String token) {
        return forgotPWService.redirectToUpatePassword(token);
    }

    @PutMapping("update/{token}")
    public ResponseDto updatePassword(@PathVariable("token") String token, @RequestBody UpdatePasswordDto updatePasswordDto) {
        return updatePasswordService.update(token, updatePasswordDto);
    }

}
