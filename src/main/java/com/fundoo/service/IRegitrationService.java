package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IRegitrationService {
    Object register(RegisterUserDto registerUserDto) throws MessagingException, UnsupportedEncodingException;
    Object verifyUser(String token) throws UnsupportedEncodingException;
}
