package com.fundoo.user.service;

import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.model.User;

import java.util.List;

public interface ILoginService {
    String login(LoginDto loginDto);

    List<User> verifyAccount();
}
