package com.fundoo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {

    @Column(nullable = false)
    @Pattern(regexp = "^((?=.*[A-Z])(?=.*[@#$%]).{6,})$" ,message = "Password Should contain One Uppercase and Symbol and greater than 6 character")
    public String password;

    @Column(nullable = false)
    @Pattern(regexp = "^((?=.*[A-Z])(?=.*[@#$%]).{6,})$" ,message = "Password Should contain One Uppercase and Symbol and greater than 6 character")
    public String confirmPassword;
}
