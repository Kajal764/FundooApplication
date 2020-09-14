package com.fundoo.user.service;


import com.fundoo.user.dto.LoginDto;
import com.fundoo.user.dto.ResponseDto;

public interface ILoginService {
    ResponseDto login(LoginDto loginDto);
}
