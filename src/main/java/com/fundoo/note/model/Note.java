package com.fundoo.note.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.label.model.Label;
import com.fundoo.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer note_Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    public String color = "#fff";

    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime editDate;

    @JsonIgnore
    private boolean isTrash;

    @JsonIgnore
    private boolean isPin;

    @JsonIgnore
    private boolean isArchive;

    @JsonIgnore
    private boolean isCollaborateNote;

    private String remainder;

    @ManyToMany(mappedBy = "noteList", fetch = FetchType.LAZY)
    private List<User> userList;

    @ManyToMany(mappedBy = "noteList", cascade = CascadeType.REMOVE)
    private List<Label> labelList;

}
