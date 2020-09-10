package com.fundoo.controller;


import com.fundoo.dto.RegisterUserDto;
import com.fundoo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundoo")
public class RegisterUserController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterUserDto registerUserDto){
       return registrationService.register(registerUserDto);
    }
}
