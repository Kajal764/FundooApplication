package com.fundoo.collaborator.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CollaborateNoteDto {

    @NotNull(message = "Note_Id must not be null")
    private int note_Id;

    @NotNull(message = "Email must not be null")
    private String email;

}
