package com.fundoo.user.service;

import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.utility.JwtUtil;
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
    public String login(LoginDto loginDto) {

        Optional<User> isEmailPresent = userRepository.findByEmail(loginDto.email);
        if (isEmailPresent.isEmpty())
            throw new LoginUserException("No such account found");
        String CLIENT_ID = isEmailPresent.get().getEmail();
        if (encoder.matches(loginDto.password, isEmailPresent.get().getPassword())) {
            if (isEmailPresent.get().isVarified() == true) {
                String token = jwtUtil.createJwtToken(CLIENT_ID);
                RedisService.setToken(CLIENT_ID, token);
                return token;
            }
            throw new LoginUserException("Please Activate your account");
        }
        throw new LoginUserException("Enter valid password");
    }
}
