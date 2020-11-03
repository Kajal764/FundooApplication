package com.fundoo.user.service;

import com.fundoo.user.dto.*;
import com.fundoo.user.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseDto register(RegisterUserDto registerUserDto) throws MessagingException, UnsupportedEncodingException;

    Object verifyUser(String token) throws UnsupportedEncodingException;

    String login(LoginDto loginDto);

    List<User> verifyAccount();

    List<User> unVerifyAccount();

    ResponseDto checkDetails(ForgotPwDto forgotPwDto) throws MessagingException;

    String redirectToUpdatePassword(String token);

    ResponseDto update(String token, UpdatePasswordDto updatePasswordDto);

    Optional<User> getUser(String email);

    String socialLogin(SocialUser socialUser);
}
