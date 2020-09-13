package com.fundoo.service;


import com.fundoo.dto.LoginDto;
import com.fundoo.dto.ResponseDto;

public interface ILoginService {
    ResponseDto login(LoginDto loginDto);
}
