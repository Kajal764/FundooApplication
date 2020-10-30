package com.fundoo.user.service;

import com.fundoo.intercepter.NoteServiceInterceptor;
import com.fundoo.user.dto.ForgotPwDto;
import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.exception.RegistrationException;
import com.fundoo.user.model.MailData;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JavaMailUtil javaMailUtil;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    RabbitMqSender rabbitMqSender;

    @Mock
    MailData mailData;

    @Test
    void givenUserDetails_WhenRegister_ItShouldSaveRegistrationDetails() throws MessagingException, UnsupportedEncodingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        when(userRepository.save(any())).thenReturn(user);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("nmdf");
        when(jwtUtil.createJwtToken(any())).thenReturn("sdjkfsdn");
        mailData.getEmail();
        mailData.getSubject();
        mailData.getMessage();
        when(rabbitMqSender.send(mailData)).thenReturn("sdfd");
        ResponseDto register = userService.register(registerUserDto);
        ResponseDto expectedResult = new ResponseDto("Registration Successful,Check mail to activate your account", 200);
        Assert.assertEquals(register.toString(), expectedResult.toString());
    }

    @Test
    void givenAlreadyRegisterDetails_WhenRegister_ItShouldNotSave() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(user));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("nmdf");
        try {
            userService.register(registerUserDto);
        } catch (RegistrationException e) {
            Assert.assertEquals(e.message, "User already register");
        }
    }

    @Test
    void givenRequestForVerify_WhenGetTokenShouldActivateVarifiedAccount() throws UnsupportedEncodingException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        when(jwtUtil.verify(token)).thenReturn(new Object());
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        userService.verifyUser(token);
        Assert.assertTrue(user.isVarified());
    }

    @Test
    void givenUserLoginCredentials_WhenLogin_ItShouldThrowException() {
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        when(userRepository.findByEmail(loginDto.email)).thenReturn(Optional.empty());

        try {
            userService.login(loginDto);
        } catch (LoginUserException e) {
            Assert.assertEquals(e.getMessage(), "No such account found");
        }
    }

    @Test
    void givenUserLoginCredentials_WhenAccountNotActivate_ItShouldThrowException() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        User user = new User(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(encoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.createJwtToken(any())).thenReturn(token);
        try {
            userService.login(loginDto);

        } catch (LoginUserException exception) {
            Assert.assertEquals(exception.getMessage(), "Please Activate your account");
        }
    }

    @Test
    void givenUserLoginCredentials_WhenLogin_ItShouldReturnToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImthamFsdzE5OThAZ21haWwuY29tIiwiaWF0IjoxNTk5OTE4NzA3LCJleHAiOjE1OTk5MTkzMDd9.VqayWCMHfA4zbjiIcBs_8Awvy9NsQNI1fIJmK3YXf5dgLc7xB1VPtLz2uo4j0V36Q3MNn5u7iOwWPAflAoS3RQ";
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        User user = new User(registerUserDto);
        user.setVarified(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(encoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.createJwtToken(any())).thenReturn(token);
        String login = userService.login(loginDto);
        Assert.assertEquals(login, token);
    }

    @Test
    void givenUserLoginPasswordWrong_WhenLogin_ItShouldThrowException() {
        LoginDto loginDto = new LoginDto("kajaldw666@gmail.com", "Asha@123");
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(encoder.matches(any(), any())).thenReturn(false);
        try {
            userService.login(loginDto);
        } catch (LoginUserException e) {
            Assert.assertEquals(e.getMessage(), "Enter valid password");
        }
    }

    @Test
    void whenGetUnverifiedUser_ItShouldReturnList() {
        ArrayList<User> list = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        user1.setVarified(true);
        list.add(user1);
        list.add(user2);
        when(userRepository.findAll()).thenReturn(list);
        List<User> userList = userService.unVerifyAccount();
        Assert.assertEquals(userList.size(), 1);
    }

    @Test
    void givenDetails_WhenForgotPassword_ItShouldReturnSuccessMessage() throws MessagingException {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        String email = "kajalw1998@gmail.com";
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(javaMailUtil.resetPwMail("kajalw1998@gmail.com", "token")).thenReturn(new SimpleMailMessage());
        when(jwtUtil.createJwtToken(any())).thenReturn(any());
        ResponseDto checkDetails = userService.checkDetails(new ForgotPwDto("kajalw1998@gmail.com"));
        Assert.assertEquals(checkDetails.message, "Email Has been sent to your account");
    }

    @Test
    void givenWrongDetails_WhenForgotPassword_ItShouldThrowException() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw1998@gmail.com", "1234","Mumbai");
        User user = new User(registerUserDto);
        String email = "kajalw1998@gmail.com";
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(javaMailUtil.resetPwMail("kajalw1998@gmail.com", "token")).thenReturn(new SimpleMailMessage());
        when(jwtUtil.createJwtToken(any())).thenReturn(any());
        try {
            userService.checkDetails(new ForgotPwDto("kajalw1998@gmail.com"));
        } catch (LoginUserException e) {
            Assert.assertEquals(e.getMessage(), "Account not found");
        }
    }

}
