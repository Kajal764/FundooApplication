package com.fundoo.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterUserDto {
    public final String firstName;
    public final String lastName;
    public final String email;
    public final String password;
}
