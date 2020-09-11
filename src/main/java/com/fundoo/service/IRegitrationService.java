package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;

import javax.mail.MessagingException;

public interface IRegitrationService {
    Object register(RegisterUserDto registerUserDto) throws MessagingException;
}
