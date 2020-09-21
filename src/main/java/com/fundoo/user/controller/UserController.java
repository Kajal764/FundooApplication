package com.fundoo.user.controller;

import com.fundoo.user.dto.*;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.User;
import com.fundoo.user.service.ForgotPWService;
import com.fundoo.user.service.LoginService;
import com.fundoo.user.service.RegistrationService;
import com.fundoo.user.service.UpdatePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/fundoo/user")
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

    @GetMapping("/verify")
    public Object validateUser(@RequestParam String token) {
        return registrationService.verifyUser(token);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String token = loginService.login(loginDto);
        response.setHeader("AuthorizeToken",token);
        return new ResponseDto("Login Successful",200);
    }

    @PostMapping(value = "/forgot_password")
    public ResponseDto forgotPassword(@RequestBody ForgotPwDto forgotPwDto) {
        return forgotPWService.checkDetails(forgotPwDto);
    }

    @GetMapping(value = "/reset_password/{token}")
    public String updatePassword(@PathVariable("token") String token) {
        return forgotPWService.redirectToUpdatePassword(token);
    }

    @PutMapping("update_password/{token}")
    public ResponseDto updatePassword(@PathVariable("token") String token, @RequestBody UpdatePasswordDto updatePasswordDto) {
        return updatePasswordService.update(token, updatePasswordDto);
    }

    @GetMapping("/verifiedUser")
    public List<User> fetchVerifiedUser(){
        List<User> userList = loginService.verifyAccount();
        if(userList.isEmpty())
          throw new LoginUserException("User Data Not Found");
        return userList;
    }
}
