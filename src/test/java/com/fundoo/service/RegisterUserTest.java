package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
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
   RegistrationService registrationService ;

    @Test
    void GivenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal","waghmare","kajalw1998@gmail.com","1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.save(any())).thenReturn(registerUser);
        RegisterUser register = registrationService.register(registerUserDto);
        Assert.assertEquals(registerUser,register);
    }

}
