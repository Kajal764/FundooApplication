package com.fundoo.user.service;

import com.fundoo.user.dto.*;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.exception.RegistrationException;
import com.fundoo.user.model.MailData;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RabbitMqSender rabbitMqSender;

    @Autowired
    MailData mailData;

    @Override
    public ResponseDto register(RegisterUserDto registerUserDto) {
        registerUserDto.password = bCryptPasswordEncoder.encode(registerUserDto.password);
        User user = new User(registerUserDto);

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register", 400);
        user.setAccountCreatedDate(LocalDateTime.now());
        User save = userRepository.save(user);
        String jwtToken = jwtUtil.createJwtToken(save.getEmail());
        String link = "http://localhost:8080/fundoo/user/verify?token=";
        mailData.setEmail(save.getEmail());
        mailData.setMessage("Registration Successful to activate your account click on this link   " + (link + jwtToken));
        mailData.setSubject("Account verification mail");
        rabbitMqSender.send(mailData);
        return new ResponseDto("Registration Successful,Check mail to activate your account", 200);
    }

    @Override
    public Object verifyUser(String token) {
        Object validateEmail = jwtUtil.verify(token);
        if (validateEmail != null) {
            Optional<User> data = userRepository.findByEmail(validateEmail.toString());
            if (data.isPresent()) {
                data.get().setVarified(true);
                userRepository.save(data.get());
                return "Verify User Successfully";
            }
        }
        return "Token Expired Register Again";
    }


    @Override
    public String login(LoginDto loginDto) {

        Optional<User> isEmailPresent = userRepository.findByEmail(loginDto.email);
        if (isEmailPresent.isEmpty())
            throw new LoginUserException("No such account found");
        String CLIENT_ID = isEmailPresent.get().getEmail();
        if (encoder.matches(loginDto.password, isEmailPresent.get().getPassword())) {
            if (isEmailPresent.get().isVarified() == true) {
                String token = jwtUtil.createJwtToken(CLIENT_ID);
                RedisService.setToken(CLIENT_ID, token);
                return token;
            }
            throw new LoginUserException("Please Activate your account");
        }
        throw new LoginUserException("Enter valid password");
    }

    @Override
    public List<User> verifyAccount() {
        List<User> varifiedUser = new ArrayList<>();
        List<User> all = userRepository.findAll();
        all.forEach(user -> {
            if (user.isVarified() == true)
                varifiedUser.add(user);
        });
        return varifiedUser;
    }

    @Override
    public List<User> unVerifyAccount() {
        List<User> userList = userRepository.findAll();
        userList.removeIf(user -> user.isVarified());
        return userList;
    }

    @Override
    public ResponseDto checkDetails(ForgotPwDto forgotPwDto) {
        Optional<User> isDetailPresent = userRepository.findByEmail(forgotPwDto.email);
        if (!isDetailPresent.isEmpty()) {
            String jwtToken = jwtUtil.createJwtToken(forgotPwDto.email);
            javaMailUtil.resetPwMail(forgotPwDto.email, jwtToken);
            return new ResponseDto("Email Has been sent to your account", 200);
        }
        throw new LoginUserException("Account not found");
    }

    @Override
    public String redirectToUpdatePassword(String token) {
        String link = "http://localhost:8080/fundoo/user/update_password/" + token;
        return link;
    }

    @Override
    public ResponseDto update(String token, UpdatePasswordDto updatePasswordDto) {
        Object verify = jwtUtil.verify(token);
        System.out.println("verify " + verify);
        Optional<User> userInfo = userRepository.findByEmail(verify.toString());
        if (userInfo.isPresent()) {
            if (updatePasswordDto.password.equals(updatePasswordDto.confirmPassword)) {
                String encodedPassword = bCryptPasswordEncoder.encode(updatePasswordDto.password);
                LocalDateTime time = LocalDateTime.now();
                userRepository.updatePasswordAndTime(encodedPassword, time, userInfo.get().getEmail());
                return new ResponseDto("Password updated succesfully", 200);
            }
            throw new LoginUserException("Password Not match");
        }
        throw new LoginUserException("Account not present");
    }

    @Override
    public Optional<User> getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent())
            return user;
        throw new LoginUserException("User Not login");

    }
}
