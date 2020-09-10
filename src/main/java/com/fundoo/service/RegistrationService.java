package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RegistrationService implements IRegitrationService {

    @Autowired
    RegisterUserRepository registerUserRepository;

    @Override
    public String register(RegisterUserDto registerUserDto) {
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        Optional<RegisterUser> byEmail = registerUserRepository.findByEmail(registerUser.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register");
        RegisterUser data = registerUserRepository.save(registerUser);
        return "Registration Successfull";
    }
}


