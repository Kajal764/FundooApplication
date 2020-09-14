package com.fundoo.user.service;

import com.fundoo.user.dto.RegisterUserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IRegitrationService {
    Object register(RegisterUserDto registerUserDto) throws MessagingException, UnsupportedEncodingException;
    Object verifyUser(String token) throws UnsupportedEncodingException;
}
