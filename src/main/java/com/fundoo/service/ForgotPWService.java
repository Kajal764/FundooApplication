package com.fundoo.service;

import com.fundoo.dto.ForgotPwDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import com.fundoo.utility.JavaMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPWService implements IForgotPWService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Override
    public ResponseDto checkDetails(ForgotPwDto forgotPwDto) {

        Optional<UserInfo> isDetailPresent = userRepository.findByEmail(forgotPwDto.email);
        if(!isDetailPresent.isEmpty()){
            if(isDetailPresent.get().getFirstName().equalsIgnoreCase(forgotPwDto.firstName) && isDetailPresent.get().getLastName().equalsIgnoreCase(forgotPwDto.lastName) ){
                return new ResponseDto("Otp Has been sent to your account", 200);
            }
        }

        throw new LoginUserException("Account not found");
    }
}

