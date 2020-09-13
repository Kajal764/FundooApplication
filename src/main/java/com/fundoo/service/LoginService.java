package com.fundoo.service;

import com.fundoo.dto.LoginDto;
import com.fundoo.exception.LoginUserException;
import com.fundoo.model.UserInfo;
import com.fundoo.repository.UserRepository;
import com.fundoo.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import com.fundoo.exception.LoginException;
import java.util.Optional;

@Service
public class LoginService implements ILoginService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    Utility utility;

    @Override
    public String login(LoginDto loginDto) {

        Optional<UserInfo> isEmailPresent = userRepository.findByEmail(loginDto.email);

        if (isEmailPresent.isEmpty())
            throw new LoginUserException("No such account found");
            //throw new LoginException("No such account found");
        String CLIENT_ID = isEmailPresent.get().getEmail();
        if (encoder.matches(loginDto.password, isEmailPresent.get().getPassword())) {
            if (isEmailPresent.get().isVarified() == true) {
                String token = utility.createJwtToken(CLIENT_ID);
                return token;
            }
            throw new LoginUserException("Please Activate your account");
        }
        throw new LoginUserException("Enter valid password");
    }
}
