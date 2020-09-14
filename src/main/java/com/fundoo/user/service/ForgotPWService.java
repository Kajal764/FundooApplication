package com.fundoo.user.service;

import com.fundoo.user.dto.ForgotPwDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.UserInfo;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPWService implements IForgotPWService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseDto checkDetails(ForgotPwDto forgotPwDto) {

        Optional<UserInfo> isDetailPresent = userRepository.findByEmail(forgotPwDto.email);
        if(!isDetailPresent.isEmpty()){
            if(isDetailPresent.get().getFirstName().equalsIgnoreCase(forgotPwDto.firstName) && isDetailPresent.get().getLastName().equalsIgnoreCase(forgotPwDto.lastName) ){
                String jwtToken = jwtUtil.createJwtToken(isDetailPresent.get().getEmail());
                javaMailUtil.resetPwMail(forgotPwDto.email,jwtToken);
                return new ResponseDto("Otp Has been sent to your account", 200);
            }
        }
        throw new LoginUserException("Account not found");
    }

    @Override
    public String redirectToUpatePassword(String token) {
        String link = "http://localhost:8080/fundoo/update/"+token;
        return link;

    }
}

