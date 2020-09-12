package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import com.fundoo.utility.Utility;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterUserTest {

    @Mock
    RegisterUserRepository registerUserRepository;

    @Mock
    JavaMailUtil javaMailUtil;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    Utility utility;

    @InjectMocks
    RegistrationService registrationService;


    @Test
    void givenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.save(any())).thenReturn(registerUser);
        when(javaMailUtil.sendMail("kajalw1998@gmail.com", "sdjfdsf")).thenReturn(new SimpleMailMessage());
        when(bCryptPasswordEncoder.encode(any())).thenReturn("nmdf");
        when(utility.createJwtToken(any())).thenReturn("sdjkfsdn");
        ResponseDto register = registrationService.register(registerUserDto);
        ResponseDto expectedResult = new ResponseDto("Registration Successful", 200);
        Assert.assertEquals(register.toString(), expectedResult.toString());
    }

    @Test
    void givenAlreadyRegisterDetails_WhenRegister_ItShouldNotSave() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(registerUserRepository.findByEmail(any())).thenReturn(java.util.Optional.of(registerUser));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("nmdf");
        try {
            registrationService.register(registerUserDto);
        } catch (RegistrationException e) {
            Assert.assertEquals(e.message, "User already register");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void givenRequestForVerify_WhenGetTokenShouldActivateVarifiedAccount() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234");
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        when(utility.verify(token)).thenReturn(new Object());
        when(registerUserRepository.findByEmail(any())).thenReturn(java.util.Optional.of(registerUser));
        when(registerUserRepository.save(registerUser)).thenReturn(registerUser);
        registrationService.verifyUser(token);
        Assert.assertTrue(registerUser.isVarified());
    }
}
