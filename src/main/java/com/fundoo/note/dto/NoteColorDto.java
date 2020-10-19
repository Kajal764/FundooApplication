package com.fundoo.note.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NoteColorDto {

    @NotNull
    public int note_id;

    @NotNull
    public String color;

}
