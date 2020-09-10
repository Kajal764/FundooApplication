package com.fundoo.service;

import com.fundoo.dto.RegisterUserDto;
import com.fundoo.model.RegisterUser;

public interface IRegitrationService {
     RegisterUser register(RegisterUserDto registerUserDto) ;
}
