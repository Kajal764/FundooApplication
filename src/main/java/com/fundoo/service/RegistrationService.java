package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements IRegitrationService {

    @Autowired
    RegisterUserRepository registerUserRepository;

    @Override
    public RegisterUser register(RegisterUserDto registerUserDto) {
        RegisterUser registerUser = new RegisterUser(registerUserDto);
        RegisterUser save = registerUserRepository.save(registerUser);
        return save;
    }
}
