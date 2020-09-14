package com.fundoo.user.service;

import com.fundoo.user.dto.LoginDto;

public interface ILoginService {
    String login(LoginDto loginDto);
}
