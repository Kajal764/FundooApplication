package com.fundoo.note.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.label.model.Label;
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

    private String title;

    private String description;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime editDate;

    private boolean isTrash;

    private boolean isPin;

    private boolean isArchive;

    @JsonIgnore
    @ManyToMany(mappedBy = "noteList", cascade = CascadeType.REMOVE)
    private List<Label> labelList;


}