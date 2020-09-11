package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class RegistrationService implements IRegitrationService {

    @Autowired
    RegisterUserRepository registerUserRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Override
    public ResponseDto register(RegisterUserDto registerUserDto) throws MessagingException {
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        Optional<RegisterUser> byEmail = registerUserRepository.findByEmail(registerUser.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register", 400);
        RegisterUser save = registerUserRepository.save(registerUser);
        javaMailUtil.sendMail(save.getEmail());
        return new ResponseDto("Registration Successful", 200);
    }

}