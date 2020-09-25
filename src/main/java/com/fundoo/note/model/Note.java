package com.fundoo.note.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fundoo.label.model.Label;
import com.fundoo.user.model.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    private String title;

    private String description;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime editDate;

    private boolean isTrash;

    private boolean isPin;

    private boolean isArchive;

    private boolean isCollaborateNote;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime remainder;

    @ManyToMany(mappedBy = "noteList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> userList;

    @ManyToMany(mappedBy = "noteList", cascade = CascadeType.REMOVE)
    private List<Label> labelList;

}