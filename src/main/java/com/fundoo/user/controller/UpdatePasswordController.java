//package com.fundoo.controller;
//
//import com.fundoo.dto.UpdatePasswordDto;
//import com.fundoo.service.UpdatePasswordService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/fundoo")
//public class UpdatePasswordController {
//
//    @Autowired
//    UpdatePasswordService updatePasswordService;
//
//
//    @PutMapping("update/{token}")
//    public Object updatePassword(@PathVariable("token") String token, @RequestBody UpdatePasswordDto updatePasswordDto) {
//        return updatePasswordService.update(token, updatePasswordDto);
//    }
//
//}
