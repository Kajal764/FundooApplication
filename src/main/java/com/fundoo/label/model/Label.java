package com.fundoo.label.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.note.model.Note;
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
@NoArgsConstructor
@ToString
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer label_Id;

    private String labelName;

    @JsonIgnore
    private LocalDateTime createdDate=LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime modifiedDate;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToMany
    private List<Note> noteList;

}
