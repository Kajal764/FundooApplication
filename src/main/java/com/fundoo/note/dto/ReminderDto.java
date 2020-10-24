package com.fundoo.note.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ReminderDto {

    @NotNull
    private int note_Id;

    public String remainder;

}
