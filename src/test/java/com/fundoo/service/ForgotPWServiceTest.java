package com.fundoo.service;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import javax.mail.MessagingException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ForgotPWServiceTest {

    @InjectMocks
    ForgotPWService forgotPWService;

    @Mock
    UserRepository userRepository;

    @Test
    void givenDetails_WhenForgotPassword_ItShouldReturnSuccessMessage() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        UserInfo userInfo = new UserInfo(registerUserDto);
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com", "kajal", "waghmare");
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(userInfo));

        ResponseDto checkDetails = forgotPWService.checkDetails(forgotPwDto);
        Assert.assertEquals(checkDetails.message,"Otp Has been sent to your account");
    }

    @Test
    void givenWrongDetails_WhenForgotPassword_ItShouldThrowException() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        UserInfo userInfo = new UserInfo(registerUserDto);
        ForgotPwDto forgotPwDto = new ForgotPwDto("kajalw1998@gmail.com", "Guddi", "waghmare");
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(userInfo));

        try{
            forgotPWService.checkDetails(forgotPwDto);
        }catch (LoginUserException e){
            Assert.assertEquals(e.getMessage(),"Account not found");
        }
    }

}

