package com.fundoo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.RegisterUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

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

    private LocalDateTime accountCreatedDate;

    private LocalDateTime accountUpdatedDate;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "U_Id")
    private List<Note> noteList;


    public User(RegisterUserDto registerUserDto) {
        this.firstName = registerUserDto.firstName;
        this.lastName = registerUserDto.lastName;
        this.email = registerUserDto.email;
        this.password = registerUserDto.password;
        this.mobileNumber = registerUserDto.mobileNumber;
    }
}
