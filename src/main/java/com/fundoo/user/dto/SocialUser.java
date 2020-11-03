package com.fundoo.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocialUser {

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "First Name is invalid")
    public String firstName;

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]{2,}$", message = "Last Name is invalid")
    public String lastName;

    @Column(nullable = false)
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Email must be valid")
    public String email;

    public String imageURL;

}
