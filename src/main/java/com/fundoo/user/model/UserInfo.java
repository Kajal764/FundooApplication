package com.fundoo.user.model;

import com.fundoo.user.dto.RegisterUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private boolean isVarified;

    public UserInfo(RegisterUserDto registerUserDto) {
        this.firstName = registerUserDto.firstName;
        this.lastName = registerUserDto.lastName;
        this.email = registerUserDto.email;
        this.password = registerUserDto.password;
        this.mobileNumber = registerUserDto.mobileNumber;
    }

}
