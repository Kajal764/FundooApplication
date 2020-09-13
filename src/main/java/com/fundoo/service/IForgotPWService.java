package com.fundoo.service;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.ResponseDto;

import javax.mail.MessagingException;

public interface IForgotPWService {
    ResponseDto checkDetails(ForgotPwDto forgotPwDto) throws MessagingException;
}
