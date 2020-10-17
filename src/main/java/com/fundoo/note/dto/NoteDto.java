package com.fundoo.note.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NoteDto {

    public int note_id;

    @NotNull
    @Length(min = 1, message = "Title must not be null")
    public String title;

    @NotNull
    @Length(min = 1, message = "Description must not be null")
    public String description;

}
