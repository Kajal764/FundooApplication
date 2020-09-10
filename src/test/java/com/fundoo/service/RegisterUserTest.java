package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

public class RegisterUserTest {

    @Mock
    RegisterUserRepository registerUserRepository;

    @InjectMocks
    RegistrationService registrationService;

    @Test
    void GivenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.save(any())).thenReturn(registerUser);
        RegisterUser register = (RegisterUser) registrationService.register(registerUserDto);
        Assert.assertEquals(registerUser, register);
    }

    @Test
    void GivenAlreadyRegisterDetails_WhenRegister_ItShouldNotSave() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.findByEmail(any())).thenReturn(java.util.Optional.of(registerUser));
        try {
            registrationService.register(registerUserDto);
        } catch (RegistrationException e) {
            Assert.assertEquals(RegistrationException.ExceptionType.ALREADY_REGISTER, e.type);
        }
    }
}
