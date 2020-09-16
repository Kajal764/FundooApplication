package com.fundoo.note.dto;

import lombok.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NoteDto {

    @NotNull(message = "Title must not be null")
    public String title;

    @NotNull(message = "Description must not be null")
    public String description;

}
