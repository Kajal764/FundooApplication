package com.fundoo.label.service;


import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.dto.MapDto;

public interface ILabelService {
    boolean createLabel(LabelDto labelDto, String token);

    boolean mapLabel(LabelDto labelDto, String email);

    boolean editLabel(LabelDto labelDto, String email);

    boolean deleteLabel(int label_id, String email);

    boolean removeNoteLabel(MapDto mapDto);
}
