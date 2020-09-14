package com.fundoo.user.service;

import com.fundoo.user.dto.UpdatePasswordDto;

public interface IUpdatePasswordService {
    Object update(String token, UpdatePasswordDto updatePasswordDto);
}
