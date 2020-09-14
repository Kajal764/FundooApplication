package com.fundoo.user.service;

import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.UserInfo;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    JwtUtil jwtUtil;


    @Test
    void givenUserLoginCredentials_WhenLogin_ItShouldThrowException() {
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        when(userRepository.findByEmail(loginDto.email)).thenReturn(Optional.empty());

        try {
            loginService.login(loginDto);
        } catch (LoginUserException e) {
            Assert.assertEquals(e.getMessage(), "No such account found");
        }
    }

    @Test
    void givenUserLoginCredentials_WhenAccountNotActivate_ItShouldThrowException() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978787878");
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        UserInfo userInfo = new UserInfo(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userInfo));
        when(encoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.createJwtToken(any())).thenReturn(token);

        try {
            loginService.login(loginDto);

        } catch (LoginUserException exception) {
            Assert.assertEquals(exception.getMessage(), "Please Activate your account");
        }
    }

    @Test
    void givenUserLoginCredentials_WhenLogin_ItShouldReturnToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978787878");
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        UserInfo userInfo = new UserInfo(registerUserDto);
        userInfo.setVarified(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userInfo));
        when(encoder.matches(any(),any())).thenReturn(true);
        when(jwtUtil.createJwtToken(any())).thenReturn(token);

        ResponseDto login = loginService.login(loginDto);

        Assert.assertEquals(login.token,token);
    }

    @Test
    void givenUserLoginPasswordWrong_WhenLogin_ItShouldThrowException() {
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","8978787878");
        UserInfo userInfo = new UserInfo(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userInfo));
        when(encoder.matches(any(),any())).thenReturn(false);

        try{
            loginService.login(loginDto);
        }catch (LoginUserException e){
            Assert.assertEquals(e.getMessage(),"Enter valid password");
        }
    }


}

