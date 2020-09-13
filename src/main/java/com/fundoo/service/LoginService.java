package com.fundoo.service;

import com.fundoo.dto.LoginDto;
import com.fundoo.dto.ResponseDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import com.fundoo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements ILoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseDto login(LoginDto loginDto) {

        Optional<UserInfo> isEmailPresent = userRepository.findByEmail(loginDto.email);
        if (isEmailPresent.isEmpty())
            throw new LoginUserException("No such account found");
        String CLIENT_ID = isEmailPresent.get().getEmail();
        if (encoder.matches(loginDto.password, isEmailPresent.get().getPassword())) {
            if (isEmailPresent.get().isVarified() == true) {
                String token = jwtUtil.createJwtToken(CLIENT_ID);
                RedisService.setToken(CLIENT_ID, token);
                return new ResponseDto(token);
            }
            throw new LoginUserException("Please Activate your account");
        }
        throw new LoginUserException("Enter valid password");
    }
}