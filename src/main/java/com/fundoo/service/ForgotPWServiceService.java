package com.fundoo.service;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import com.fundoo.utility.JavaMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class ForgotPWServiceService implements IForgotPWService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Override
    public String checkDetails(ForgotPwDto forgotPwDto) throws MessagingException {

        Optional<UserInfo> isDetailPresent = userRepository.findByEmail(forgotPwDto.email);
        if(isDetailPresent.get().getFirstName().equalsIgnoreCase(forgotPwDto.firstName) && isDetailPresent.get().getLastName().equalsIgnoreCase(forgotPwDto.lastName) ){
            return "Otp Has been sent to your account";
        }
        throw new LoginUserException("Account not found");
    }
}
