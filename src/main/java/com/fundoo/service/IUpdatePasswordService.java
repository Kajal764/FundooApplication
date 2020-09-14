package com.fundoo.service;

import com.fundoo.dto.UpdatePasswordDto;

public interface IUpdatePasswordService {
    Object update(String token, UpdatePasswordDto updatePasswordDto);
}
