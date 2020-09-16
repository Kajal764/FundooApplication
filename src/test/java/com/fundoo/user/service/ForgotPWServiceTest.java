package com.fundoo.user.service;

import com.fundoo.user.dto.ForgotPwDto;
import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ForgotPWServiceTest {

    @InjectMocks
    ForgotPWService forgotPWService;

    @Mock
    UserRepository userRepository;

    @Mock
    JavaMailUtil javaMailUtil;

    @Mock
    JwtUtil jwtUtil;

    @Test
    void givenDetails_WhenForgotPassword_ItShouldReturnSuccessMessage() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        User user = new User(registerUserDto);
        String email="kajalw1998@gmail.com";
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(javaMailUtil.resetPwMail("kajalw1998@gmail.com","token")).thenReturn(new SimpleMailMessage());
        when(jwtUtil.createJwtToken(any())).thenReturn(any());
        ResponseDto checkDetails = forgotPWService.checkDetails(new ForgotPwDto("kajalw1998@gmail.com"));
        Assert.assertEquals(checkDetails.message,"Email Has been sent to your account");
    }

    @Test
    void givenWrongDetails_WhenForgotPassword_ItShouldThrowException() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        User user = new User(registerUserDto);
        String email="kajalw1998@gmail.com";
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(javaMailUtil.resetPwMail("kajalw1998@gmail.com","token")).thenReturn(new SimpleMailMessage());
        when(jwtUtil.createJwtToken(any())).thenReturn(any());
        try{
            forgotPWService.checkDetails(new ForgotPwDto("kajalw1998@gmail.com"));
        }catch (LoginUserException e){
            Assert.assertEquals(e.getMessage(),"Account not found");
        }
    }

}

