package com.fundoo.label.model;
import com.fundoo.note.model.Note;
import com.fundoo.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer label_Id;

    private String labelName;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Note> noteList;

}
