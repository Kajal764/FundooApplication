package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.UpdatePasswordDto;
import com.fundoo.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdatePasswordServiceTest {

    @Autowired
    UpdatePasswordService updatePasswordService;

//    @Test
//    void givenRequestWithToken_WhenResetPassword_ItShouldSuccessfullyReset() {
//
//        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
//
//        //UserInfo userInfo = new UserInfo(registerUserDto);
//
//
//
//        String token="eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsZHc2NjZAZ21haWwuY29tIiwiaWF0IjoxNjAwMDEyMTAwLCJleHAiOjE2MDAwMTI3MDB9.KQ1lhtjyYKJLnrDWCnBm0mclpreuQtxo8BAeVOaRvWo8SXvrpezewcdk_ZpCMS-Xz6_NP0hNoL9Nk15_B3pAhA";
//        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(token, "123@Hii", "123@Hii");
//        updatePasswordService.update(updatePasswordDto);
//    }
}
