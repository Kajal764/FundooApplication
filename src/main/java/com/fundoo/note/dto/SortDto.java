package com.fundoo.note.dto;

import com.fundoo.note.enumerations.SortBaseOn;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SortDto {

    private SortBaseOn sortBaseOn;
    private String type;


}
