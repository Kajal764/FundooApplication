package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterUserTest {

    @Mock
    RegisterUserRepository registerUserRepository;

    @Mock
    JavaMailUtil javaMailUtil;

    @InjectMocks
    RegistrationService registrationService;

    @Test
    void givenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.save(any())).thenReturn(registerUser);
        when(javaMailUtil.sendMail(any())).thenReturn(new SimpleMailMessage());
        ResponseDto register = registrationService.register(registerUserDto);
        ResponseDto expectedResult = new ResponseDto("Registration Successful", 200);
        Assert.assertEquals(register.toString(), expectedResult.toString());
    }

    @Test
    void givenAlreadyRegisterDetails_WhenRegister_ItShouldNotSave() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.findByEmail(any())).thenReturn(java.util.Optional.of(registerUser));
        try {
            registrationService.register(registerUserDto);
        } catch (RegistrationException e) {
            Assert.assertEquals(e.message, "User already register");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
