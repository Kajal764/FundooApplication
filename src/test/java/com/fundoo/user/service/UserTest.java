package com.fundoo.user.service;

import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.RegistrationException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
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
public class UserTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JavaMailUtil javaMailUtil;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    RegistrationService registrationService;


    @Test
    void givenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        User user = new User(registerUserDto);
        when(userRepository.save(any())).thenReturn(user);
        when(javaMailUtil.sendMail("kajalw1998@gmail.com", "sdjfdsf")).thenReturn(new SimpleMailMessage());
        when(bCryptPasswordEncoder.encode(any())).thenReturn("nmdf");
        when(jwtUtil.createJwtToken(any())).thenReturn("sdjkfsdn");
        ResponseDto register = registrationService.register(registerUserDto);
        ResponseDto expectedResult = new ResponseDto("Registration Successful", 200);
        Assert.assertEquals(register.toString(), expectedResult.toString());
    }

    @Test
    void givenAlreadyRegisterDetails_WhenRegister_ItShouldNotSave() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        User user = new User(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(user));
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
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978675645");
        User user = new User(registerUserDto);
        when(jwtUtil.verify(token)).thenReturn(new Object());
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        registrationService.verifyUser(token);
        Assert.assertTrue(user.isVarified());
    }
}
