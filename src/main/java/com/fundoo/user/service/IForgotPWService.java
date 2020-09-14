package com.fundoo.user.service;

import com.fundoo.user.dto.ForgotPwDto;
import com.fundoo.user.dto.ResponseDto;

import javax.mail.MessagingException;

public interface IForgotPWService {
    ResponseDto checkDetails(ForgotPwDto forgotPwDto) throws MessagingException;

    Object redirectToUpatePassword(String token);
}
