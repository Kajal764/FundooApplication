package com.fundoo.note.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NoteDto {

    public String title;

    public String description;

}
