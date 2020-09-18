package com.fundoo.label.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LabelDto {

    private int note_Id;

    private int label_Id;

    @NotNull(message = "Label must not be null")
    private String labelName;

}
