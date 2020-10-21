package com.fundoo.note.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    public String color;

    @JsonIgnore
    private boolean isPin;

    @JsonIgnore
    private boolean isArchive;

}
