package com.fundoo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.label.model.Label;
import com.fundoo.note.model.Note;
import com.fundoo.user.dto.RegisterUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isVarified;

    private String city;

    public String imageURL;

    public boolean isSocialUser;

    @JsonIgnore
    private LocalDateTime accountCreatedDate;

    @JsonIgnore
    private LocalDateTime accountUpdatedDate;

    @JsonIgnore
    @ManyToMany
    private List<Note> noteList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Label> labelList;

    public User(RegisterUserDto registerUserDto) {
        this.firstName = registerUserDto.firstName;
        this.lastName = registerUserDto.lastName;
        this.email = registerUserDto.email;
        this.password = registerUserDto.password;
        this.city = registerUserDto.city;
    }
}
