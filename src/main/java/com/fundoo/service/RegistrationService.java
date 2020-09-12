package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.RegistrationException;
import com.fundoo.model.RegisterUser;
import com.fundoo.repository.RegisterUserRepository;
import com.fundoo.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class RegistrationService implements IRegitrationService {

    @Autowired
    RegisterUserRepository registerUserRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utility utility;

    @Override
    public ResponseDto register(RegisterUserDto registerUserDto) throws MessagingException {
        registerUserDto.password = bCryptPasswordEncoder.encode(registerUserDto.password);
        RegisterUser registerUser = new RegisterUser(registerUserDto);

        Optional<RegisterUser> byEmail = registerUserRepository.findByEmail(registerUser.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register", 400);
        RegisterUser save = registerUserRepository.save(registerUser);
        String jwtToken = utility.createJwtToken(save.getEmail());
        javaMailUtil.sendMail(save.getEmail(), jwtToken);
        return new ResponseDto("Registration Successful", 200);
    }

    @Override
    public Object verifyUser(String token) {
        Object validateEmail = utility.verify(token);
        if (validateEmail != null) {
            Optional<RegisterUser> data = registerUserRepository.findByEmail(validateEmail.toString());
            if (data.isPresent()) {
                data.get().setVarified(true);
                registerUserRepository.save(data.get());
                return "Verify User Successfully";
            }
        }
        return "Token Expired Register Again";
    }
}
