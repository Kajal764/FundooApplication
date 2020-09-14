package com.fundoo.service;

import com.fundoo.dto.ResponseDto;
import com.fundoo.dto.UpdatePasswordDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import com.fundoo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePasswordService implements IUpdatePasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Object update(String token, UpdatePasswordDto updatePasswordDto) {
        Object verify = jwtUtil.verify(token);
        Optional<UserInfo> userInfo = userRepository.findByEmail(verify.toString());
        if (userInfo.isPresent()) {
            if (updatePasswordDto.password.equals(updatePasswordDto.confirmPassword)) {
                String encodedPassword = bCryptPasswordEncoder.encode(updatePasswordDto.password);
                userRepository.updatePassword(encodedPassword, userInfo.get().getEmail());
                return new ResponseDto("Password updated succesfully",200);
            }
            throw new LoginUserException("Password Not match");
        }
        throw new LoginUserException("Account not present");
    }
}

