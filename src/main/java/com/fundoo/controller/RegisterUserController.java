package com.fundoo.controller;


import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundoo")
public class RegisterUserController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return registrationService.register(registerUserDto);
    }
}
