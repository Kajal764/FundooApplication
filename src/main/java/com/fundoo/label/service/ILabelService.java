package com.fundoo.label.service;


import com.fundoo.label.dto.LabelDto;

public interface ILabelService {
    boolean createLabel(LabelDto labelDto, String token) ;
}
