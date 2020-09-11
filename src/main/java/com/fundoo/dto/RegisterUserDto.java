package com.fundoo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "First Name is invalid")
    public String firstName;

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "Last Name is invalid")
    public String lastName;

    @Column(nullable = false)
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Email must be valid")
    public String email;

    @Column(nullable = false)
    @Pattern(regexp = "^((?=.*[A-Z])(?=.*[@#$%]).{6,})$" ,message = "Password Should contain One Uppercase and Symbol and greater than 6 character")
    public String password;

}
