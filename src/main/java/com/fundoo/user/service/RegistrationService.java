package com.fundoo.user.service;

import com.fundoo.user.dto.RegisterUserDto;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.RegistrationException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JavaMailUtil;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class RegistrationService implements IRegitrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailUtil javaMailUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseDto register(RegisterUserDto registerUserDto) throws MessagingException {
        registerUserDto.password = bCryptPasswordEncoder.encode(registerUserDto.password);
        User user = new User(registerUserDto);

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent())
            throw new RegistrationException("User already register", 400);
        User save = userRepository.save(user);
        String jwtToken = jwtUtil.createJwtToken(save.getEmail());
        javaMailUtil.sendMail(save.getEmail(), jwtToken);
        return new ResponseDto("Registration Successful", 200);
    }

    @Override
    public Object verifyUser(String token) {
        Object validateEmail = jwtUtil.verify(token);
        if (validateEmail != null) {
            Optional<User> data = userRepository.findByEmail(validateEmail.toString());
            if (data.isPresent()) {
                data.get().setVarified(true);
                userRepository.save(data.get());
                return "Verify User Successfully";
            }
        }
        return "Token Expired Register Again";
    }
}
