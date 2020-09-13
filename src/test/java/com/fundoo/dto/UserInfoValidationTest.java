package com.fundoo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserInfoValidationTest {

    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void GivenInvalidName_whenValidation_ItShouldNotAccept() {
        RegisterUserDto registerUserDto = new RegisterUserDto("", "waghmare", "kajalw1998@gmail.com", "Asha#ghf","8978675645");
        Set<ConstraintViolation<RegisterUserDto>> validate = validator.validate(registerUserDto);
        assertThat(validate.iterator().next().getMessage(), is("First Name is invalid"));
    }

    @Test
    public void GivenInvalidEmail_whenValidation_ItShouldNotAccept() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw199gmail.com", "Asha#ghf","8978675645");
        Set<ConstraintViolation<RegisterUserDto>> validate = validator.validate(registerUserDto);
        assertThat(validate.iterator().next().getMessage(), is("Email must be valid"));
    }

    @Test
    public void GivenInvalidPassword_whenValidation_ItShouldNotAccept() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw199@gmail.com", "as23","8978675645");
        Set<ConstraintViolation<RegisterUserDto>> validate = validator.validate(registerUserDto);
        assertThat(validate.iterator().next().getMessage(), is("Password Should contain One Uppercase and Symbol and greater than 6 character"));
    }

    @Test
    public void GivenInvalidMobileNo_whenValidation_ItShouldNotAccept() {
        RegisterUserDto registerUserDto = new RegisterUserDto("kajal", "waghmare", "kajalw199@gmail.com", "123@Hii","78675645");
        Set<ConstraintViolation<RegisterUserDto>> validate = validator.validate(registerUserDto);
        assertThat(validate.iterator().next().getMessage(), is("Invalid mobile no"));
    }

}
