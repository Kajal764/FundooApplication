package com.fundoo.service;

import com.fundoo.dto.ForgotPwDto;

import javax.mail.MessagingException;

public interface IForgotPWService {
    String checkDetails(ForgotPwDto forgotPwDto) throws MessagingException;
}
